package com.example.android.firstresponse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class AnxietyManagement : AppCompatActivity() {

    private lateinit var webView1: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anxiety_management)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        // Change title of action bar
        supportActionBar?.title = "ANXIETY MANAGEMENT"

        // Show back button on action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup first WebView
        webView1 = findViewById(R.id.webView1)
        webView1.settings.javaScriptEnabled = true
        webView1.webViewClient = WebViewClient()

        val videoId = "HQm7xRjl6-I"
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        webView1.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>",
            "text/html",
            "utf-8"
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView1.canGoBack()) {
            webView1.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
