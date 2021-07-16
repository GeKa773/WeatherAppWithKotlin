package com.gekaradchenko.weatherappwithkotlin.locationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gekaradchenko.weatherappwithkotlin.R;
import com.gekaradchenko.weatherappwithkotlin.WeatherActivity;
import com.gekaradchenko.weatherappwithkotlin.database.Location;
import com.gekaradchenko.weatherappwithkotlin.database.LocationDao;
import com.gekaradchenko.weatherappwithkotlin.database.LocationDatabase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    private EditText nameCityEditText;
    private Button addLocationButton;
    private GoogleMap googleMap;
    private LatLng latLng;
    private Geocoder geocoder;
    private MarkerOptions markerOptions;

    private boolean isPermissionGranter;

    private List<Address> addressList;


    private Thread getLocationThread;

    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        nameCityEditText = findViewById(R.id.nameCityEditText);
        addLocationButton = findViewById(R.id.addLocationButton);


        myCheckPermission();

        if (isPermissionGranter) {
            if (checkGooglePlaServices()) {
                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.container, supportMapFragment).commit();
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Google Play service not available", Toast.LENGTH_SHORT).show();

            }
        }

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getLatLng();

                } catch (Exception e) {

                }
                startActivity(new Intent(AddLocationActivity.this, WeatherActivity.class));

            }

            private void getLatLng() {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            addressList = geocoder.getFromLocationName(getCity(), 1);
                            if (addressList.size() > 0) {
                                latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                                markerOptions.position(latLng);
                                markerOptions.title(getCity());

                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        googleMap.addMarker(markerOptions);
                                        googleMap.animateCamera(cameraUpdate);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                insertLocationToDB();

                                            }
                                        }).start();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            Toast.makeText(AddLocationActivity.this, "Can`t find city", Toast.LENGTH_SHORT).show();
                        }
                        getCity();
                    }
                };
                getLocationThread = new Thread(runnable);
                getLocationThread.start();

            }

            private void insertLocationToDB() {
                LocationDao database = LocationDatabase.Companion.getInstance(getApplication()).getLocationDao();
                database.insertLocation(new Location(0, addressList.get(0).getLatitude(), addressList.get(0).getLongitude()));
            }

            private String getCity() {
                String cityName = nameCityEditText.getText().toString();
                if (cityName.equals(null) | cityName.equals("")) {
                    cityName = "kiev";
                    nameCityEditText.setText("kiev");
                }
                return cityName;
            }
        });

    }

    private void myCheckPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                isPermissionGranter = true;
                Toast.makeText(AddLocationActivity.this, "Permission Granter", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private boolean checkGooglePlaServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                    Toast.makeText(AddLocationActivity.this, "User Cancelked  Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }

        return false;
    }


    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        latLng = new LatLng(0, 0);
        markerOptions = new MarkerOptions();
        geocoder = new Geocoder(AddLocationActivity.this, Locale.getDefault());
    }
}