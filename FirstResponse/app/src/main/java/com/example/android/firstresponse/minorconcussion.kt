package com.example.android.firstresponse

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class minorconcussion : BaseActivity() {

    private lateinit var webView1: WebView
    private lateinit var fabSave: FloatingActionButton
    private val topicId = "minor_concussion" // Unique ID for the topic
    private val topicTitle = "Minor Concussions" // Topic title for display purposes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minor_concussion)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))
        supportActionBar?.title = "MINOR CONCUSSION"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize WebView
        webView1 = findViewById(R.id.webView1)
        webView1.settings.javaScriptEnabled = true
        webView1.webViewClient = WebViewClient()
        val videoId = "K88n8m4eJwM"
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        webView1.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>",
            "text/html",
            "utf-8"
        )

        // Initialize FloatingActionButton for saving the topic
        fabSave = findViewById(R.id.fab_save)
        updateFabIcon()

        fabSave.setOnClickListener {
            toggleSaveTopic()
        }

        // Set up the call button
        val callButton1 = findViewById<Button>(R.id.callbutton1)
        callButton1.setOnClickListener {
            val intent = Intent(this, HelplineActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to toggle save status
    private fun toggleSaveTopic() {
        val sharedPref = getSharedPreferences("SavedTopics", MODE_PRIVATE)
        val gson = Gson()

        // Load saved topics from SharedPreferences
        val savedTopicsJson = sharedPref.getString("savedTopicsJson", "[]")
        val type = object : TypeToken<MutableList<SavedTopic>>() {}.type
        val savedTopicsSet: MutableList<SavedTopic> = gson.fromJson(savedTopicsJson, type)

        // Check if the topic is already saved
        val isSaved = savedTopicsSet.any { it.id == topicId }

        if (isSaved) {
            // Remove the topic if it's already saved
            savedTopicsSet.removeAll { it.id == topicId }
            Toast.makeText(this, "Topic removed", Toast.LENGTH_SHORT).show()
        } else {
            // Add the topic if it's not saved
            savedTopicsSet.add(SavedTopic(topicId, topicTitle))
            Toast.makeText(this, "Topic saved", Toast.LENGTH_SHORT).show()
        }

        // Save the updated list of topics to SharedPreferences
        val updatedTopicsJson = gson.toJson(savedTopicsSet)
        with(sharedPref.edit()) {
            putString("savedTopicsJson", updatedTopicsJson)
            apply()
        }
        updateFabIcon()
    }

    // Function to update FloatingActionButton icon based on save state
    private fun updateFabIcon() {
        val sharedPref = getSharedPreferences("SavedTopics", MODE_PRIVATE)
        val gson = Gson()

        // Load saved topics from SharedPreferences
        val savedTopicsJson = sharedPref.getString("savedTopicsJson", "[]")
        val type = object : TypeToken<List<SavedTopic>>() {}.type
        val savedTopicsSet: List<SavedTopic> = gson.fromJson(savedTopicsJson, type)

        // Check if the topic is already saved
        val isSaved = savedTopicsSet.any { it.id == topicId }
        val iconResId = if (isSaved) R.drawable.saved_red else R.drawable.saved
        fabSave.setImageDrawable(ContextCompat.getDrawable(this, iconResId))
    }

    // Function to handle the back button in the action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (webView1.canGoBack()) {
            webView1.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
