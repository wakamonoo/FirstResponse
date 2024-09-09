package com.example.android.firstresponse

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class WhatsWrongActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsSpinner: Spinner
    private lateinit var nextButton: Button
    private lateinit var thatAllButton: Button
    private lateinit var resultTextView: TextView

    private var currentStage = 0
    private val symptomsSelected = mutableListOf<String>()

    private val stages = listOf(
        listOf("Fever", "Cough", "Headache", "Rash", "Muscle Pain"),
        listOf("Nausea", "Fatigue", "Sore Throat", "Loss of Appetite", "Sweating"),
        listOf("Shortness of Breath", "Chest Pain", "Dizziness", "Confusion", "Severe Weakness"),
        listOf("Vomiting", "Diarrhea", "Abdominal Pain", "Bloating", "Constipation"),
        listOf("Joint Pain", "Stiffness", "Swelling", "Redness", "Warmth"),
        listOf("None of the Above") // Final stage button
    )

    private val diagnosis = mapOf(
        listOf("Fever", "Cough", "Headache") to "Flu",
        listOf("Headache", "Nausea", "Dizziness") to "Migraine",
        listOf("Fever", "Sore Throat", "Rash") to "Strep Throat",
        listOf("Shortness of Breath", "Chest Pain") to "Heart Attack",
        listOf("Vomiting", "Diarrhea") to "Gastroenteritis",
        listOf("Joint Pain", "Stiffness", "Swelling") to "Rheumatoid Arthritis",
        listOf("Chest Pain", "Shortness of Breath", "Dizziness") to "Pulmonary Embolism",
        listOf("Headache", "Nausea", "Vomiting") to "Migraine",
        listOf("Abdominal Pain", "Bloating", "Constipation") to "Irritable Bowel Syndrome",
        listOf("Fever", "Rash", "Joint Pain") to "Systemic Lupus Erythematosus"
        // Add more conditions here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_wrong)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))
        supportActionBar?.title = "SAFETY AND PREPAREDNESS"

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView)
        optionsSpinner = findViewById(R.id.optionsSpinner)
        nextButton = findViewById(R.id.nextButton)
        thatAllButton = findViewById(R.id.thatAllButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Set up Spinner
        setupSpinner()

        // Start with the first stage
        displayStage()

        // Set click listener for the buttons
        nextButton.setOnClickListener {
            handleNextButton()
        }

        thatAllButton.setOnClickListener {
            handleThatAllButton()
        }
    }

    private fun setupSpinner() {
        // Ensure currentStage is within bounds
        if (currentStage >= stages.size) return
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stages[currentStage])
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionsSpinner.adapter = adapter
    }

    private fun displayStage() {
        if (currentStage >= stages.size) return  // Prevent out-of-bounds errors
        val symptoms = stages[currentStage]
        questionTextView.text = "Select your symptom:"
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, symptoms)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionsSpinner.adapter = adapter
    }

    private fun handleNextButton() {
        val selectedSymptom = optionsSpinner.selectedItem?.toString()
        if (selectedSymptom.isNullOrEmpty() || selectedSymptom == "None of the Above") {
            return  // Skip if "None of the Above" is selected or no selection
        }

        symptomsSelected.add(selectedSymptom)

        if (currentStage < stages.size - 1) {
            currentStage++
            displayStage()
        } else {
            handleResults()
        }
    }

    private fun handleThatAllButton() {
        handleResults() // Proceed to results if "That's All" is selected
    }

    private fun handleResults() {
        val possibleConditions = diagnosis.map { entry ->
            val matchingSymptoms = entry.key.count { symptomsSelected.contains(it) }
            val confidencePercentage = (matchingSymptoms.toDouble() / entry.key.size * 100).toInt()
            entry.value to confidencePercentage
        }

        val bestMatch = possibleConditions.maxByOrNull { it.second } ?: "Unknown Condition" to 0
        val result = if (bestMatch.second > 0) {
            "Possible Condition: ${bestMatch.first} (Confidence: ${bestMatch.second}%)"
        } else {
            "No matching conditions found."
        }
        resultTextView.text = result
        resultTextView.visibility = View.VISIBLE  // Corrected visibility type

        // Hide other UI elements
        questionTextView.visibility = View.GONE  // Corrected visibility type
        optionsSpinner.visibility = View.GONE  // Corrected visibility type
        nextButton.visibility = View.GONE  // Corrected visibility type
        thatAllButton.visibility = View.GONE  // Corrected visibility type
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()  // Close the current activity and return to the previous one
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
