package com.example.android.firstresponse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class consumption : BaseActivity() {

    private lateinit var webView1: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        //to change title of action bar of respective activity
        getSupportActionBar()?.setTitle("CONSUMPTION");

        //to show back button on action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //webview
        webView1 = findViewById(R.id.webView)
        webView1.settings.javaScriptEnabled = true
        webView1.webViewClient = WebViewClient()


        val videoId = "NxHNxmJv2bQ"
        val videoUrl = "https://www.youtube.com/embed/$videoId"

        webView1.loadData(
            "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>",
            "text/html",
            "utf-8"
        )
    }
    //function to make back button work
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
        }


        else {
            super.onBackPressed()
        }
    }

}