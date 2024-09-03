package com.example.android.firstresponse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class HelplineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpline)

        // Hide the Action Bar for this activity
        supportActionBar?.hide()

        // Initialize LottieAnimationView
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView?.apply {
            setAnimation(R.raw.emerg) // Ensure this matches your animation file
            playAnimation()
            repeatCount = LottieDrawable.INFINITE // Optional: loop animation
        }

        val qrtPolanguiPhoneNumber = "09123456789"
        val qrtLigaoPhoneNumber = "09876543210"
        val qrtOasPhoneNumber = "09012345678"

        val buttonQrtPolangui = findViewById<Button>(R.id.button_qrt_polangui)
        val buttonQrtLigao = findViewById<Button>(R.id.button_qrt_ligao)
        val buttonQrtOas = findViewById<Button>(R.id.button_qrt_oas)

        buttonQrtPolangui.setOnClickListener {
            dialPhoneNumber(qrtPolanguiPhoneNumber)
        }

        buttonQrtLigao.setOnClickListener {
            dialPhoneNumber(qrtLigaoPhoneNumber)
        }

        buttonQrtOas.setOnClickListener {
            dialPhoneNumber(qrtOasPhoneNumber)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomHome -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // Apply transition animations
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                R.id.bottomHelpline -> {
                    // Already in HelplineActivity, no need to navigate
                    true
                }
                else -> false
            }
        }

        // Set the Helpline item as selected
        bottomNavigationView.menu.findItem(R.id.bottomHelpline).isChecked = true
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
}
