package com.example.android.firstresponse

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class QuizActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var answersGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var quizData: List<Triple<String, Array<String>, Int>>
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz)

        supportActionBar?.hide()

        // Initialize LottieAnimationView
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.apply {
            setAnimation(R.raw.think) // Ensure this matches your animation file in res/raw/
            playAnimation()
            repeatCount = LottieDrawable.INFINITE // Loop animation
        }

        questionTextView = findViewById(R.id.questionTextView)
        answersGroup = findViewById(R.id.answersGroup)
        submitButton = findViewById(R.id.submitButton)

        try {
            // Initialize your quiz data here
            val questionArray = resources.getStringArray(R.array.quiz_questions).toList()
            val answerArrays = listOf(
                resources.getStringArray(R.array.quiz_answers_1),
                resources.getStringArray(R.array.quiz_answers_2),
                resources.getStringArray(R.array.quiz_answers_3),
                resources.getStringArray(R.array.quiz_answers_4),
                resources.getStringArray(R.array.quiz_answers_5),
                resources.getStringArray(R.array.quiz_answers_6),
                resources.getStringArray(R.array.quiz_answers_7),
                resources.getStringArray(R.array.quiz_answers_8),
                resources.getStringArray(R.array.quiz_answers_9),
                resources.getStringArray(R.array.quiz_answers_10),
                resources.getStringArray(R.array.quiz_answers_11),
                resources.getStringArray(R.array.quiz_answers_12),
                resources.getStringArray(R.array.quiz_answers_13),
                resources.getStringArray(R.array.quiz_answers_14),
                resources.getStringArray(R.array.quiz_answers_15)
            )
            val correctAnswerArray = resources.getIntArray(R.array.correct_answers)

            if (questionArray.isEmpty() || answerArrays.any { it.isEmpty() } || correctAnswerArray.isEmpty()) {
                throw IllegalStateException("Quiz data not properly initialized")
            }

            // Combine the question, answers, and correct answer into a single list of triples
            quizData = questionArray.indices.map { index ->
                Triple(questionArray[index], answerArrays[index], correctAnswerArray[index])
            }.shuffled() // Shuffle them together

            Log.d("QuizActivity", "Quiz Data: $quizData")

            loadQuestion()

            submitButton.setOnClickListener {
                if (checkAnswer()) {
                    score++
                }
                currentQuestionIndex++
                if (currentQuestionIndex < quizData.size) {
                    loadQuestion()
                } else {
                    showResult()
                }
            }
        } catch (e: Exception) {
            Log.e("QuizActivity", "Error initializing quiz data", e)
        }
    }

    private fun loadQuestion() {
        try {
            if (currentQuestionIndex >= quizData.size) {
                throw IndexOutOfBoundsException("Current question index out of bounds")
            }

            val (question, answers, _) = quizData[currentQuestionIndex]

            questionTextView.text = question
            answersGroup.removeAllViews()
            answers.forEachIndexed { index, answer ->
                val radioButton = RadioButton(this)
                radioButton.text = answer
                radioButton.id = View.generateViewId() // Generate a unique ID for each RadioButton
                answersGroup.addView(radioButton)
            }
        } catch (e: Exception) {
            Log.e("QuizActivity", "Error loading question", e)
        }
    }

    private fun checkAnswer(): Boolean {
        val selectedId = answersGroup.checkedRadioButtonId
        val selectedRadioButton = findViewById<RadioButton>(selectedId)
        val (_, _, correctAnswerIndex) = quizData[currentQuestionIndex]

        return selectedRadioButton != null &&
                answersGroup.indexOfChild(selectedRadioButton) == correctAnswerIndex
    }

    private fun showResult() {
        try {
            val intent = Intent(this, QuizResultActivity::class.java)
            intent.putExtra("score", score)
            finish() // Close QuizActivity
            startActivity(intent) // Open QuizResultActivity
        } catch (e: Exception) {
            Log.e("QuizActivity", "Error showing result", e)
        }
    }
}
