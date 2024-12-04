package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_login)

                // Initialize Firebase Auth
                auth = FirebaseAuth.getInstance()

                // Initialize Firebase Auth state listener
                auth.addAuthStateListener { firebaseAuth ->
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                                // If the user is logged in, navigate to UserInfoActivity
                                startActivity(Intent(this, UserInfoActivity::class.java))
                                finish()  // Close LoginActivity
                        }
                }

                // Initialize Biometric authentication if the user is not logged in
                executor = ContextCompat.getMainExecutor(this)
                biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()

                                // After successful authentication, navigate to UserInfoActivity
                                startActivity(Intent(this@LoginActivity, UserInfoActivity::class.java))
                                finish()  // Close LoginActivity
                        }

                        override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                })

                biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Fingerprint Authentication")
                        .setSubtitle("Login with your fingerprint")
                        .setNegativeButtonText("Cancel")
                        .build()

                findViewById<Button>(R.id.btn_fingerprint).setOnClickListener {
                        biometricPrompt.authenticate(biometricPromptInfo)
                }

                // Add this line to bind btnGoToRegister
                val btnGoToRegister: Button = findViewById(R.id.btn_go_to_register)

                // Set register button click listener
                btnGoToRegister.setOnClickListener {
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                        finish()  // Close LoginActivity after navigating to RegisterActivity
                }
        }
}
