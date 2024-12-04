package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executor

class RegisterActivity : AppCompatActivity() {

        private lateinit var etUsername: EditText
        private lateinit var etPassword: EditText
        private lateinit var etEmail: EditText
        private lateinit var btnRegister: ImageButton
        private lateinit var btnGoToLogin: Button
        private lateinit var cbFingerprint: CheckBox  // Fingerprint checkbox

        private lateinit var auth: FirebaseAuth
        private lateinit var database: DatabaseReference

        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var executor: Executor

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_register)

                etUsername = findViewById(R.id.et_register_username)
                etPassword = findViewById(R.id.et_register_password)
                etEmail = findViewById(R.id.et_register_email)
                btnRegister = findViewById(R.id.btn_register)
                btnGoToLogin = findViewById(R.id.btn_go_to_login)
                cbFingerprint = findViewById(R.id.cb_fingerprint)

                auth = FirebaseAuth.getInstance()
                database = FirebaseDatabase.getInstance().reference

                // Initialize the BiometricPrompt
                executor = ContextCompat.getMainExecutor(this)
                biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                // Fingerprint authentication succeeded, proceed with registration
                                registerUser()
                        }

                        override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                Toast.makeText(applicationContext, "Fingerprint authentication failed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                Toast.makeText(applicationContext, "Fingerprint authentication error: $errString", Toast.LENGTH_SHORT).show()
                        }
                })

                // Set up button to enable/disable based on fingerprint option
                btnRegister.isEnabled = false // Initially disabled

                // Enable the register button if fingerprint is either registered or not required
                cbFingerprint.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                                // If checkbox is checked, disable fingerprint authentication
                                btnRegister.isEnabled = true // Allow registration directly
                        } else {
                                // If checkbox is unchecked, enable fingerprint authentication
                                btnRegister.isEnabled = true
                        }
                }

                btnRegister.setOnClickListener {
                        val username = etUsername.text.toString().trim()
                        val password = etPassword.text.toString().trim()
                        val email = etEmail.text.toString().trim()
                        val isFingerprintRequired = cbFingerprint.isChecked

                        // Ensure the fields are not empty
                        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                        }

                        // If fingerprint is required (checkbox unchecked), perform fingerprint authentication
                        if (!isFingerprintRequired) {
                                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                                        .setTitle("Fingerprint Authentication")
                                        .setSubtitle("Please authenticate using your fingerprint")
                                        .setNegativeButtonText("Cancel")
                                        .build()

                                biometricPrompt.authenticate(promptInfo)
                        } else {
                                // If fingerprint is not required (checkbox checked), directly register the user
                                registerUser()
                        }
                }

                btnGoToLogin.setOnClickListener {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                }
        }

        // Method to register the user in Firebase
        private fun registerUser() {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val email = etEmail.text.toString().trim()

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        val userId = auth.currentUser?.uid
                                        val userMap = mapOf(
                                                "username" to username,
                                                "email" to email,
                                                "fingerprintRegistered" to cbFingerprint.isChecked // Store fingerprint registration status
                                        )
                                        userId?.let {
                                                database.child("users").child(it).setValue(userMap)
                                                        .addOnSuccessListener {
                                                                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                                                                val intent = Intent(this, LoginActivity::class.java)
                                                                startActivity(intent)
                                                                finish()
                                                        }
                                                        .addOnFailureListener { e ->
                                                                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                                        }
                                        }
                                } else {
                                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
        }
}