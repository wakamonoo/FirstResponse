package com.example.android.firstresponse

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView



class WhatsWrongActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsSpinner: Spinner
    private lateinit var nextButton: Button
    private lateinit var thatAllButton: Button
    private lateinit var resultTextView: TextView

    private var currentStage = 0
    private val answersSelected = mutableMapOf<String, String>()

    private val stages = listOf(
        "When did the symptoms begin?",
        "Where does the pain or discomfort originate?",
        "How intense are the symptoms on a scale of 1-10?",
        "How often do the symptoms occur?",
        "Describe the type of pain.",
        "Is there any swelling in the affected area?",
        "Is the area red or inflamed?",
        "Are you taking any medications or undergoing any treatments?",
        "Have your symptoms improved, worsened, or remained the same?",
        "Are there any activities or factors that seem to worsen your symptoms?"
    )

    private val diagnosis = mapOf(
        // Existing conditions
        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Head",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Frequently",
            "Describe the type of pain." to "Sharp",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Migraine",

        listOf(
            "When did the symptoms begin?" to "Within the last week",
            "Where does the pain or discomfort originate?" to "Abdomen",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Cramping",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Irritable Bowel Syndrome",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Chest",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Constantly",
            "Describe the type of pain." to "Pressure",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Heart Attack",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Back",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Aching",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Muscle Strain",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Legs",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Frequently",
            "Describe the type of pain." to "Cramping",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Deep Vein Thrombosis",

        listOf(
            "When did the symptoms begin?" to "Within the last month",
            "Where does the pain or discomfort originate?" to "Head",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Throbbing",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "Yes",
            "Have your symptoms improved, worsened, or remained the same?" to "Improved",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Sinusitis",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Abdomen",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Constantly",
            "Describe the type of pain." to "Cramping",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "Yes",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Appendicitis",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Chest",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Frequently",
            "Describe the type of pain." to "Burning",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Gastroesophageal Reflux Disease (GERD)",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Head",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Pounding",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Improved",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Tension Headache",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Joints",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Aching",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "Yes",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Rheumatoid Arthritis",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Feet",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Frequently",
            "Describe the type of pain." to "Burning",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "Yes",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Gout",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Back",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Constantly",
            "Describe the type of pain." to "Stiffness",
            "Is there any swelling in the affected area?" to "Yes",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Herniated Disc",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Head",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Dull",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Cluster Headache",

        listOf(
            "When did the symptoms begin?" to "Within the last week",
            "Where does the pain or discomfort originate?" to "Neck",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Frequently",
            "Describe the type of pain." to "Stiffness",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Cervical Spondylosis",

        listOf(
            "When did the symptoms begin?" to "Within the last 24 hours",
            "Where does the pain or discomfort originate?" to "Hands",
            "How intense are the symptoms on a scale of 1-10?" to "Moderate",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Numbness",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "No",
            "Have your symptoms improved, worsened, or remained the same?" to "Remained the same",
            "Are there any activities or factors that seem to worsen your symptoms?" to "No"
        ) to "Carpal Tunnel Syndrome",

        listOf(
            "When did the symptoms begin?" to "Within the last month",
            "Where does the pain or discomfort originate?" to "Stomach",
            "How intense are the symptoms on a scale of 1-10?" to "High",
            "How often do the symptoms occur?" to "Occasionally",
            "Describe the type of pain." to "Cramping",
            "Is there any swelling in the affected area?" to "No",
            "Is the area red or inflamed?" to "No",
            "Are you taking any medications or undergoing any treatments?" to "Yes",
            "Have your symptoms improved, worsened, or remained the same?" to "Worsened",
            "Are there any activities or factors that seem to worsen your symptoms?" to "Yes"
        ) to "Peptic Ulcer"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_wrong)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.shadow2
                )
            )
        )
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Handle the Up button click
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSpinner() {
        val options = when (currentStage) {
            0 -> listOf(
                "Within the last 24 hours",
                "Within the last week",
                "Within the last month",
                "More than a month ago"
            )

            1 -> listOf("Head", "Chest", "Abdomen", "Legs", "Arms", "Back", "Joints")
            2 -> listOf("Low (1-3)", "Moderate (4-6)", "High (7-10)")
            3 -> listOf("Rarely", "Occasionally", "Frequently", "Constantly")
            4 -> listOf("Sharp", "Dull", "Throbbing", "Aching", "Burning", "Cramping")
            5 -> listOf("Yes", "No")
            6 -> listOf("Yes", "No")
            7 -> listOf("Yes", "No", "Not sure")
            8 -> listOf("Improved", "Worsened", "Remained the same")
            9 -> listOf("Yes", "No", "Not sure")
            else -> listOf("Select an answer")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionsSpinner.adapter = adapter
    }

    private fun displayStage() {
        if (currentStage >= stages.size) return  // Prevent out-of-bounds errors
        questionTextView.text = stages[currentStage]
        setupSpinner()
    }

    private fun handleNextButton() {
        val selectedAnswer = optionsSpinner.selectedItem?.toString()
        if (selectedAnswer.isNullOrEmpty() || selectedAnswer == "Select an answer") {
            return  // Skip if no valid selection
        }

        answersSelected[stages[currentStage]] = selectedAnswer

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
        val selectedLocation = answersSelected["Where does the pain or discomfort originate?"]

        val possibleConditions = diagnosis.map { (criteria, condition) ->
            // Give more weight to the location of pain
            val locationWeight = 2  // Weight factor for location of pain
            val totalCriteria = criteria.size
            var matchingCriteria = 0

            // Find the specific pair for location of pain
            val locationPair =
                criteria.find { it.first == "Where does the pain or discomfort originate?" }
            val isRelevant = locationPair?.second == selectedLocation
            if (!isRelevant) {
                return@map condition to 0
            }

            criteria.forEach { (question, answer) ->
                if (answersSelected[question] == answer) {
                    matchingCriteria += if (question == "Where does the pain or discomfort originate?") {
                        locationWeight
                    } else {
                        1
                    }
                }
            }

            val confidencePercentage =
                (matchingCriteria.toDouble() / (totalCriteria + locationWeight - 1) * 100).toInt()
            condition to confidencePercentage
        }

        // Filter out conditions with 0 confidence and find the best match
        val bestMatch = possibleConditions.filter { it.second > 0 }.maxByOrNull { it.second }
            ?: "Unknown Condition" to 0
        val result = if (bestMatch.second > 0) {
            "Possible Condition: ${bestMatch.first} (Confidence: ${bestMatch.second}%)"
        } else {
            "Kindly Provide More Information!"
        }
        resultTextView.text = result
        resultTextView.visibility = View.VISIBLE

        // Hide other UI elements
        questionTextView.visibility = View.GONE
        optionsSpinner.visibility = View.GONE
        nextButton.visibility = View.GONE
        thatAllButton.visibility = View.GONE

        // Delay showing the pop-up message
        Handler(Looper.getMainLooper()).postDelayed({
            showAlert()
        }, 1000) // 3 seconds delay
    }

    private fun showAlert() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)
        val imageView: ImageView = dialogView.findViewById(R.id.dialog_image)
        val messageTextView: TextView = dialogView.findViewById(R.id.dialog_message)
        val okButton: Button = dialogView.findViewById(R.id.dialog_button)

        // Customize the message
        messageTextView.text =
            "It's still advisable to see a personal health authority for a more accurate analysis."

        // Create and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        okButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
