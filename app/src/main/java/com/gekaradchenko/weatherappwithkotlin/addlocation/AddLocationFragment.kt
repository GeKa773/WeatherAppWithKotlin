package com.gekaradchenko.weatherappwithkotlin.addlocation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gekaradchenko.weatherappwithkotlin.R
import com.gekaradchenko.weatherappwithkotlin.database.LocationDatabase
import com.gekaradchenko.weatherappwithkotlin.databinding.FragmentAddLocationBinding
import com.gekaradchenko.weatherappwithkotlin.locationmap.AddLocationActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class AddLocationFragment : Fragment() {


    var isPermissionGranter: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAddLocationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_location, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = LocationDatabase.getInstance(application).locationDao

        val viewModelFactory = AddLocationViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(AddLocationViewModel::class.java)

        binding.locationButton.setOnClickListener {

            viewModel.myCheckPermission()
            viewModel.isPermissionGranter.observe(viewLifecycleOwner, Observer {
                when {
                    it -> {

                        startActivity(Intent(context, AddLocationActivity::class.java))
                    }
                    else -> {
                        Toast.makeText(context,
                            "Google Play service not available",
                            Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        intent.data = viewModel.uri
                        startActivity(intent)

                    }

                }
            })

        }


        return binding.root
    }


    private fun myCheckPermission() {
        Dexter.withContext(context).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    isPermissionGranter = true
                    Toast.makeText(context, "Permission Granter", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", context!!.packageName, "")
                    intent.data = uri
                    startActivity(intent)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken,
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }

}