package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executor

class RegisterActivity : AppCompatActivity() {

        private lateinit var etUsername: EditText
        private lateinit var etPassword: EditText
        private lateinit var etEmail: EditText
        private lateinit var btnRegister: ImageButton
        private lateinit var btnGoToLogin: Button
        private lateinit var cbFingerprint: CheckBox
        private lateinit var btnSendOtp: Button // OTP Button

        private lateinit var auth: FirebaseAuth
        private lateinit var database: DatabaseReference

        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var executor: Executor
        private var isEmailVerified = false

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_register)

                etUsername = findViewById(R.id.et_register_username)
                etPassword = findViewById(R.id.et_register_password)
                etEmail = findViewById(R.id.et_register_email)
                btnRegister = findViewById(R.id.btn_register)
                btnGoToLogin = findViewById(R.id.btn_go_to_login)
                cbFingerprint = findViewById(R.id.cb_fingerprint)
                btnSendOtp = findViewById(R.id.btn_send_otp)

                auth = FirebaseAuth.getInstance()
                database = FirebaseDatabase.getInstance().reference

                executor = ContextCompat.getMainExecutor(this)
                biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                registerUser()
                        }
                })

                btnSendOtp.setOnClickListener {
                        sendOtpToEmail()
                }

                btnRegister.setOnClickListener {
                        if (!isEmailVerified) {
                                Toast.makeText(this, "Please verify your email before registering.", Toast.LENGTH_SHORT).show()
                                return@setOnClickListener
                        }
                        if (!cbFingerprint.isChecked) {
                                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                                        .setTitle("Fingerprint Authentication")
                                        .setSubtitle("Authenticate to register")
                                        .setNegativeButtonText("Cancel")
                                        .build()
                                biometricPrompt.authenticate(promptInfo)
                        } else {
                                registerUser()
                        }
                }

                btnGoToLogin.setOnClickListener {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                }
        }

        // This method will check email verification status every time the activity resumes
        override fun onResume() {
                super.onResume()
                checkEmailVerificationStatus()
        }

        private fun sendOtpToEmail() {
                val email = etEmail.text.toString().trim()
                if (email.isEmpty()) {
                        Toast.makeText(this, "Please enter an email address.", Toast.LENGTH_SHORT).show()
                        return
                }

                auth.createUserWithEmailAndPassword(email, "TemporaryPassword123")
                        .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        val user = auth.currentUser
                                        user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                                                if (emailTask.isSuccessful) {
                                                        Toast.makeText(this, "Verification sent to $email. Verify to proceed.", Toast.LENGTH_SHORT).show()
                                                        checkEmailVerificationStatus() // Check the email verification status after sending OTP
                                                } else {
                                                        Toast.makeText(this, "Failed to send verification email: ${emailTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                } else {
                                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
        }

        private fun checkEmailVerificationStatus() {
                val currentUser = auth.currentUser
                currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                        if (reloadTask.isSuccessful) {
                                if (currentUser.isEmailVerified) {
                                        isEmailVerified = true
                                        updateEmailVerifiedUI() // Update UI to reflect email verification
                                } else {
                                        Toast.makeText(this, "Please verify your email to proceed.", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                                Toast.makeText(this, "Failed to reload user data: ${reloadTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                }
        }

        private fun updateEmailVerifiedUI() {
                // Update the UI to show that the email is verified
                btnSendOtp.text = "Email Verified"
                btnSendOtp.isEnabled = false // Disable the OTP button

                // Change the button color (for example, to green to indicate successful verification)
                btnSendOtp.setBackgroundColor(ContextCompat.getColor(this, R.color.colorVerified)) // Define the color in your colors.xml

                // Indicate user registration success
                Toast.makeText(this, "Email Verified. Proceeding with registration.", Toast.LENGTH_SHORT).show()
        }

        private fun registerUser() {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val email = etEmail.text.toString().trim()

                val currentUser = auth.currentUser
                currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                        if (currentUser.isEmailVerified) {
                                val userId = currentUser.uid
                                val userMap = mapOf(
                                        "username" to username,
                                        "email" to email,
                                        "fingerprintRegistered" to cbFingerprint.isChecked
                                )
                                database.child("users").child(userId).setValue(userMap)
                                        .addOnSuccessListener {
                                                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this, LoginActivity::class.java))
                                                finish()
                                        }
                                        .addOnFailureListener { e ->
                                                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                        } else {
                                Toast.makeText(this, "Please verify your email to proceed.", Toast.LENGTH_SHORT).show()
                        }
                }
        }
}
