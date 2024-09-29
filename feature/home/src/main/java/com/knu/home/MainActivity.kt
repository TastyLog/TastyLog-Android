package com.knu.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.knu.common.view.viewBinding
import com.knu.retastylog.home.R
import com.knu.retastylog.home.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(MainActivityBinding::inflate)

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationServiceLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        locationServiceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isLocationServiceEnabled()) {
                checkLocationPermission()
            }
        }

        handleLocationServiceAndPermission()
    }

    private fun handleLocationServiceAndPermission() {
        if (isLocationServiceEnabled()) {
            checkLocationPermission()
        } else {
            showLocationServiceRequest()
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE,
            )
        } else {
            getLastKnownLocation()
        }
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let { initMapFragment(it) }
                }
                .addOnFailureListener {
                    // fail handler
                }
        } else {
            explainPermissionRequirement()
        }
    }

    private fun initMapFragment(location: Location) {
        val fragment = MapFragment.newInstance(location)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastKnownLocation()
        } else {
            explainPermissionRequirement()
        }
    }

    private fun explainPermissionRequirement() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.location_permission_required_title))
            .setMessage(getString(R.string.location_permission_required_message))
            .setPositiveButton(getString(R.string.request_permission)) { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE,
                )
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun isLocationServiceEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER,
        )
    }

    private fun showLocationServiceRequest() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.location_service_required_title))
            .setMessage(getString(R.string.location_service_required_message))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                locationServiceLauncher.launch(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
