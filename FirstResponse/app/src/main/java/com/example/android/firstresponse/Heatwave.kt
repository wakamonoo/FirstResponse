package com.example.android.firstresponse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class Heatwave : AppCompatActivity() {

    private lateinit var webView1: WebView
    private lateinit var webView2: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trauma_informed_care)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        // Change title of action bar
        supportActionBar?.title = "HEATWAVE"

        // Show back button on action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup first WebView
        webView1 = findViewById(R.id.webView1)
        webView1.settings.javaScriptEnabled = true
        webView1.webViewClient = WebViewClient()

        val videoId = "yourVideoId1"
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        webView1.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>",
            "text/html",
            "utf-8"
        )

        // Setup second WebView
        webView2 = findViewById(R.id.webView2)
        webView2.settings.javaScriptEnabled = true
        webView2.webViewClient = WebViewClient()

        val videoId2 = "yourVideoId2"
        val videoUrl2 = "https://www.youtube.com/embed/$videoId2"
        webView2.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl2\" frameborder=\"0\" allowfullscreen></iframe>",
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
        } else if (webView2.canGoBack()) {
            webView2.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
