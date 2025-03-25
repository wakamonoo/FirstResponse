package com.example.android.firstresponse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserInfoActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvBirthday: TextView
    private lateinit var btnSendLocation: Button
    private lateinit var btnLogout: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        // Initialize Firebase and location client
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://firstresponseapp-b0184-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Bind views
        tvUsername = findViewById(R.id.tvUsername)
        tvFullName = findViewById(R.id.tvFullName)
        tvEmail = findViewById(R.id.tvEmail)
        tvAddress = findViewById(R.id.tvAddress)
        tvBirthday = findViewById(R.id.tvBirthday)
        btnSendLocation = findViewById(R.id.btnSendLocation)
        btnLogout = findViewById(R.id.btnLogout)

        // Check if user is logged in and email verified
        val currentUser = auth.currentUser
        if (currentUser == null || !currentUser.isEmailVerified) {
            Toast.makeText(this, "Please log in and verify your email.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch user details from Firebase
        fetchUserDetails(currentUser.uid)

        // Set up the send location button
        btnSendLocation.setOnClickListener {
            checkAndRequestLocationPermission()
        }

        // Set up logout button
        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchUserDetails(userId: String) {
        database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                tvUsername.text = "Username: ${snapshot.child("username").value?.toString() ?: "N/A"}"
                tvFullName.text = "Full Name: ${snapshot.child("fullname").value?.toString() ?: "N/A"}"
                tvEmail.text = "Email: ${snapshot.child("email").value?.toString() ?: "N/A"}"
                tvAddress.text = "Address: ${snapshot.child("address").value?.toString() ?: "N/A"}"
                tvBirthday.text = "Birthday: ${snapshot.child("birthday").value?.toString() ?: "N/A"}"
            } else {
                tvUsername.text = "User data not found"
            }
        }.addOnFailureListener {
            tvUsername.text = "Failed to load data: ${it.message}"
        }
    }

    private fun checkAndRequestLocationPermission() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed to get location
            sendHelpWithLocation()
        } else {
            // Permission not granted, request permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
    }

    private fun sendHelpWithLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    // Prepare data for help request
                    val helpRequest = mapOf(
                        "id" to System.currentTimeMillis(),
                        "username" to tvUsername.text.removePrefix("Username: ").toString(),
                        "email" to tvEmail.text.removePrefix("Email: ").toString(),
                        "full_name" to tvFullName.text.removePrefix("Full Name: ").toString(),
                        "address" to tvAddress.text.removePrefix("Address: ").toString(),
                        "location" to "(${location.latitude}, ${location.longitude})"
                    )

                    // Save data to Firebase
                    database.child("emergencies").push().setValue(helpRequest)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Help request sent successfully!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to send help: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Unable to retrieve location. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to access location: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permission denied. Cannot access location.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendHelpWithLocation()
        } else {
            Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show()
        }
    }
}
