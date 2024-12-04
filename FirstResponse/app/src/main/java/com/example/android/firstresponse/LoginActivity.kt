package com.example.android.firstresponse

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.android.firstresponse.R
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {

        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_login)

                executor = ContextCompat.getMainExecutor(this)
                biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                super.onAuthenticationError(errorCode, errString)
                                Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                Toast.makeText(applicationContext, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                                // Proceed to next screen or perform desired action
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
        }
}
