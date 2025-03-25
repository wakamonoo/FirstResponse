package com.example.android.firstresponse

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.Toast

class FlashlightActivity : BaseActivity() {
    private lateinit var flashlightManager: FlashlightManager
    private var mediaPlayer: MediaPlayer? = null // MediaPlayer for whistle sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashlight)

        // Load animations
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)
        val releaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release)

        // Initialize custom toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.customToolbar)
        setSupportActionBar(toolbar)

        // Set the title for the Toolbar
        supportActionBar?.title = "FLASHLIGHT"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.red))

        // Show back button on the Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.back)

        // Set up back button click listener with animation
        toolbar.setNavigationOnClickListener {
            it.startAnimation(pressAnim)
            it.postDelayed({
                it.startAnimation(releaseAnim)
                finish()
            }, pressAnim.duration)
        }

        flashlightManager = FlashlightManager(this)

        // Request Camera permission if not already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }

        // Set up buttons for flashlight modes
        findViewById<Button>(R.id.btn_flashlight_on).setOnClickListener {
            if (isCameraPermissionGranted()) {
                flashlightManager.turnOn()
            } else {
                showPermissionRequiredToast()
            }
        }

        findViewById<Button>(R.id.btn_flashlight_off).setOnClickListener {
            flashlightManager.turnOff()
        }

        findViewById<Button>(R.id.btn_flashlight_strobe).setOnClickListener {
            if (isCameraPermissionGranted()) {
                flashlightManager.strobe(500) // Adjust frequency as needed
            } else {
                showPermissionRequiredToast()
            }
        }

        findViewById<Button>(R.id.btn_flashlight_sos).setOnClickListener {
            if (isCameraPermissionGranted()) {
                flashlightManager.sos()
            } else {
                showPermissionRequiredToast()
            }
        }

        // SOS with Alarm Button
        findViewById<Button>(R.id.btn_flashlight_alarm).setOnClickListener {
            if (isCameraPermissionGranted()) {
                flashlightManager.sosWithAlarm() // Start SOS with alarm sound
            } else {
                showPermissionRequiredToast()
            }
        }

        // Stop Flashlight Button
        findViewById<Button>(R.id.btn_flashlight_stop).setOnClickListener {
            flashlightManager.stopFlashing()
            stopMedia() // Stop any ongoing alarm or whistle
        }

        // Whistle Feature Button
        findViewById<Button>(R.id.btn_flashlight_whistle).setOnClickListener {
            playWhistle()
        }

        // Show instructions to the user
        showInstructions()
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionRequiredToast() {
        Toast.makeText(this, "Camera permission is required to use the flashlight.", Toast.LENGTH_SHORT).show()
    }

    private fun showInstructions() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_flashlight_instructions, null)

        val dialogMessage: TextView = dialogView.findViewById(R.id.dialog_message)
        val instructionTitle = getString(R.string.dialog_title_flashlight)
        dialogMessage.text = getString(R.string.dialog_message)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle(instructionTitle)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val dialogButton = dialogView.findViewById<Button>(R.id.dialog_button)
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun playWhistle() {
        // Stop any existing media before starting
        stopMedia()

        // Initialize MediaPlayer and start playing in a loop
        mediaPlayer = MediaPlayer.create(this, R.raw.whistle_sound).apply {
            isLooping = true // Set looping to true
            start()
        }
    }

    private fun stopMedia() {
        // Stop and release the MediaPlayer
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        flashlightManager.stopFlashing()
        stopMedia()
    }
}
