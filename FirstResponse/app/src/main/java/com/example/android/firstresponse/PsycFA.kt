package com.example.android.firstresponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class PsycFA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_psycfa)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        getSupportActionBar()?.setTitle("Psychological First Aid")

        // calling the action bar
        val actionBar = supportActionBar

        // showing the back button in the action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Switching to Acute Grief activity
        val buttonAcuteGrief = findViewById<Button>(R.id.buttonAcuteGrief)
        buttonAcuteGrief.setOnClickListener {
            val intent = Intent(this, AcuteGrief::class.java)
            startActivity(intent)
        }

        // Switching to Anxiety Management activity
        val buttonAnxiety = findViewById<Button>(R.id.buttonAnxiety)
        buttonAnxiety.setOnClickListener {
            val intent = Intent(this, AnxietyManagement::class.java)
            startActivity(intent)
        }

        // Switching to Panic Attack Response activity
        val buttonPanic = findViewById<Button>(R.id.buttonPanic)
        buttonPanic.setOnClickListener {
            val intent = Intent(this, PanicAttackResponse::class.java)
            startActivity(intent)
        }

        // Switching to Trauma-Informed Care activity
        val buttonTraumaCare = findViewById<Button>(R.id.buttonTraumaCare)
        buttonTraumaCare.setOnClickListener {
            val intent = Intent(this, TraumaInformedCare::class.java)
            startActivity(intent)
        }

        // Switching to Grounding Techniques activity
        val buttonGrounding = findViewById<Button>(R.id.buttonGrounding)
        buttonGrounding.setOnClickListener {
            val intent = Intent(this, GroundingTechniques::class.java)
            startActivity(intent)
        }

        // Switching to Stress Reduction activity
        val buttonStressReduction = findViewById<Button>(R.id.buttonStressReduction)
        buttonStressReduction.setOnClickListener {
            val intent = Intent(this, StressReduction::class.java)
            startActivity(intent)
        }
    }
}
