package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FirstAid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_aid)

        // Load animations
        val pressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)
        val releaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release)

        // Set up the back button in the action bar
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            // Start the press animation
            backButton.startAnimation(pressAnim)

            // Delayed action to finish activity and apply release animation
            backButton.postDelayed({
                // Apply release animation (optional)
                backButton.startAnimation(releaseAnim)

                // Close the activity
                finish()
            }, pressAnim.duration)
        }

        // Set up button click listeners for switching to different activities

        // CPR Activity
        val buttonCPR = findViewById<Button>(R.id.buttonCPR)
        buttonCPR.setOnClickListener {
            val intent = Intent(this, CPR::class.java)
            startActivity(intent)
        }

        // Shock Activity
        val buttonShock = findViewById<Button>(R.id.buttonShock)
        buttonShock.setOnClickListener {
            val intent = Intent(this, shock::class.java)
            startActivity(intent)
        }

        // Splints Activity
        val buttonSplints = findViewById<Button>(R.id.buttonSplints)
        buttonSplints.setOnClickListener {
            val intent = Intent(this, splints::class.java)
            startActivity(intent)
        }

        // Choking Activity
        val buttonChoking = findViewById<Button>(R.id.buttonChoking)
        buttonChoking.setOnClickListener {
            val intent = Intent(this, choking::class.java)
            startActivity(intent)
        }

        // Burns Activity
        val buttonBurns = findViewById<Button>(R.id.buttonBurns)
        buttonBurns.setOnClickListener {
            val intent = Intent(this, burns::class.java)
            startActivity(intent)
        }

        // Seizures Activity
        val buttonSeizures = findViewById<Button>(R.id.buttonSeizures)
        buttonSeizures.setOnClickListener {
            val intent = Intent(this, seizures::class.java)
            startActivity(intent)
        }
    }
}
