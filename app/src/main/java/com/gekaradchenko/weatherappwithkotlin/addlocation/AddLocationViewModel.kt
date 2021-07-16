package com.gekaradchenko.weatherappwithkotlin.addlocation

import android.Manifest
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AddLocationViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val uri: Uri = Uri.fromParts("package", app.packageName, "")

    private val _isPermissionGranter = MutableLiveData<Boolean>()
    val isPermissionGranter: LiveData<Boolean>
        get() = _isPermissionGranter




    fun myCheckPermission() {
        Dexter.withContext(app).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    _isPermissionGranter.value = true
                    Toast.makeText(app, "Permission Granter", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {
                    _isPermissionGranter.value = false
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken,
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}