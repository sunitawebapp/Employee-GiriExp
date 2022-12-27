package com.sunitawebapp.employee_giriexp.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
/**
 * Constants Values
 */
const val INTERVAL = 5000L
const val FASTEST_INTERVAL = 2500L

class CurrentLocationViewModel(application: Application)  : AndroidViewModel(application) {
    /**
     * MutableLiveData private field to get/save location updated values
     */
    private val locationData =
        CurrentLocationLiveData(application)

    /**
     * LiveData a public field to observe the changes of location
     */
    val getLocationData: LiveData<Location> = locationData
    class CurrentLocationLiveData(context: Context)  : MutableLiveData<Location>() {
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        override fun onActive() {
            super.onActive()


            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.also {
                        setLocationData(it)
                    }
                }

            startLocationUpdates()
        }

        fun setLocationData(location: Location){
            value=location
        }

        /**
         * Static object of location request
         */
        companion object {
            val locationRequest: LocationRequest = LocationRequest.create()
                .apply {
                    interval = INTERVAL
                    fastestInterval = FASTEST_INTERVAL
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
        }



        /**
         * Callback that triggers on location updates available
         */
        private val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    setLocationData(location)
                }
            }
        }


        /**
         * Initiate Location Updates using Fused Location Provider and
         * attaching callback to listen location updates
         */
        private fun startLocationUpdates() {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }


    }


}
