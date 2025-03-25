package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth
        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo
        private lateinit var emailField: EditText
        private lateinit var passwordField: EditText

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_login)

                auth = FirebaseAuth.getInstance()
                emailField = findViewById(R.id.et_email)
                passwordField = findViewById(R.id.et_password)

                findViewById<Button>(R.id.btn_login).apply {
                        setOnClickListener { performEmailLogin() }
                }

                findViewById<Button>(R.id.btn_biometric_login).apply {
                        setOnClickListener { handleBiometricLogin() }
                }

                findViewById<Button>(R.id.btn_go_to_register).apply {
                        setOnClickListener {
                                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                                finish()
                        }
                }

                checkLoggedInUser()
                initBiometricAuth()
        }

        // Handle Biometric Login Button Click
        private fun handleBiometricLogin() {
                if (isBiometricAvailable()) {
                        biometricPrompt.authenticate(biometricPromptInfo)
                } else {
                        showToast("Biometric authentication is not available.")
                }
        }

        // Check if the user is already logged in
        private fun checkLoggedInUser() {
                val currentUser = auth.currentUser
                currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                                if (currentUser.isEmailVerified) {
                                        navigateToUserInfo()
                                } else {
                                        showToast("Please verify your email to proceed.")
                                }
                        } else {
                                logError("Error reloading user", reloadTask.exception)
                                showToast("Failed to reload user data.")
                        }
                }
        }

        private fun isBiometricAvailable(): Boolean {
                val biometricManager = BiometricManager.from(this)
                return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                        BiometricManager.BIOMETRIC_SUCCESS -> true
                        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> showToast("No biometric hardware available").run { false }
                        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> showToast("Biometric hardware is unavailable").run { false }
                        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                                showToast("No fingerprints enrolled. Please enroll in device settings.")
                                false
                        }
                        else -> false
                }
        }

        private fun initBiometricAuth() {
                executor = ContextCompat.getMainExecutor(this)
                biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                showToast("Authentication error: $errString")
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                showToast("Authentication succeeded!")
                                checkEmailVerification()
                        }

                        override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                showToast("Authentication failed")
                        }
                })

                biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Fingerprint Authentication")
                        .setSubtitle("Login with your fingerprint")
                        .setNegativeButtonText("Cancel")
                        .build()
        }

        private fun performEmailLogin() {
                val email = emailField.text.toString().trim()
                val password = passwordField.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                        showToast("Please enter email and password")
                        return
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        showToast("Please enter a valid email address")
                        return
                }

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                                checkEmailVerification()
                        } else {
                                logError("Login failed", task.exception)
                                showToast("Authentication failed: ${task.exception?.message}")
                        }
                }
        }

        private fun checkEmailVerification() {
                val currentUser = auth.currentUser
                currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                                if (currentUser.isEmailVerified) {
                                        navigateToUserInfo()
                                } else {
                                        showToast("Email not verified. Please verify to proceed.")
                                }
                        } else {
                                logError("Error reloading user", reloadTask.exception)
                                showToast("Failed to reload user data.")
                        }
                }
        }

        private fun navigateToUserInfo() {
                startActivity(Intent(this, UserInfoActivity::class.java))
                finish()
        }

        // Helper Methods
        private fun showToast(message: String) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        private fun logError(message: String, exception: Exception?) {
                Log.e("LoginActivity", "$message: ${exception?.message}")
        }
}
