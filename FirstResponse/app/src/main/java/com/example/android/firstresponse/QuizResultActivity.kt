package com.example.android.firstresponse

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class QuizResultActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var retryButton: Button
    private lateinit var homeButton: Button

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)

        supportActionBar?.hide()

        // Initialize LottieAnimationView
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.apply {
            setAnimation(R.raw.result) // Ensure this matches your animation file in res/raw/
            playAnimation()
            repeatCount = LottieDrawable.INFINITE // Loop animation
        }

        resultTextView = findViewById(R.id.resultTextView)
        retryButton = findViewById(R.id.retryButton)
        homeButton = findViewById(R.id.homeButton)

        val score = intent.getIntExtra("score", 0)
        val highestScore = sharedPreferences.getInt("highest_score", 0)

        // Update the highest score if the current score is higher
        if (score > highestScore) {
            sharedPreferences.edit().putInt("highest_score", score).apply()
        }

        // Display current score and highest score
        resultTextView.text = "Your Score: $score\nHighest Score: ${sharedPreferences.getInt("highest_score", 0)}"

        retryButton.setOnClickListener {
            finish() // Close QuizResultActivity
            startActivity(Intent(this, QuizActivity::class.java)) // Restart QuizActivity
        }

        homeButton.setOnClickListener {
            finish() // Close QuizResultActivity
            startActivity(Intent(this, MainActivity::class.java)) // Go back to MainActivity
        }
    }
}