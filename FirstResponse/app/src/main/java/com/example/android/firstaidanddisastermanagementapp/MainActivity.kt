package com.example.android.firstaidanddisastermanagementapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.graphics.drawable.ColorDrawable
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var buttonQuiz: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val LOCATION_SETTINGS_REQUEST_CODE = 2
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        drawerLayout = findViewById(R.id.drawer_Layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize buttons and their click listeners
        findViewById<Button>(R.id.button1).setOnClickListener {
            startActivity(Intent(this, FirstAid::class.java))
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, DisasterManagement::class.java))
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, BasicSanitation::class.java))
        }

        buttonQuiz = findViewById(R.id.buttonQuiz)
        buttonQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }

        // Bottom navigation setup
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomHelpline -> {
                    val intent = Intent(this, HelplineActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                else -> false
            }
        }

        // Initialize navigation drawer and its item selected listener
        findViewById<NavigationView>(R.id.navigationView).setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.location -> {
                    if (checkLocationPermission()) {
                        enableLocationSettings()
                    } else {
                        requestLocationPermission()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Set the home menu item as selected
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.menu.findItem(R.id.bottomHome).isChecked = true

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.menu.findItem(R.id.sideHome).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        // Request location permission
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun enableLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            Log.d(TAG, "Location settings are satisfied.")
            // Directly open Google Maps after ensuring location settings
            openGoogleMaps()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(this, LOCATION_SETTINGS_REQUEST_CODE)
                } catch (sendEx: Exception) {
                    Log.e(TAG, "Error resolving location settings: ${sendEx.localizedMessage}")
                }
            } else {
                Log.e(TAG, "Location settings are not adequate.")
                Toast.makeText(this, "Location settings are not adequate, and cannot be fixed here. Fix in Settings.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun openGoogleMaps() {
        // Define your locations
        val location1 = Pair(13.292603487659566, 123.49065310834811) // RHU Polangui
        val location2 = Pair(13.257769416930843, 123.49944847998052) // RHU Oas
        val location3 = Pair(13.239677184699186, 123.55644399532457) // Josefina Belmonte Duran Albay Provincial Hospital

        // Default location URL (for demonstration; update based on your logic)
        val nearestLocationUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=13.292603487659566,123.49065310834811")

        val mapIntent = Intent(Intent.ACTION_VIEW, nearestLocationUri)
        startActivity(mapIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationSettings()
            } else {
                Log.e(TAG, "Location permission denied")
                Toast.makeText(this, "Permission denied. Please grant location access to use this feature.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
            openGoogleMaps()
        }
    }
}
