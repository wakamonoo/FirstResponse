package com.example.android.firstaidanddisastermanagementapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var buttonQuiz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))


        // Initialize DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_Layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("")

        // Initialize buttons and their click listeners
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, FirstAid::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this, DisasterManagement::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.button3)
        button5.setOnClickListener {
            val intent = Intent(this, BasicSanitation::class.java)
            startActivity(intent)
        }

        // Initialize buttonQuiz
        buttonQuiz = findViewById(R.id.buttonQuiz)
        buttonQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        // Bottom navigation setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottomHelpline -> {
                    val intent = Intent(this, HelplineActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    true
                }
                else -> false
            }
        }

        // Initialize navigation drawer and its item selected listener
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.location -> {
                    val sideDrawerLocation = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Rural+Health+Unit+-+Polangui/@13.2937115,123.4860495,17z/data=!4m10!1m2!2m1!1sRhu+polangui!3m6!1s0x33a1a1cd683fec01:0xd7b1601e3dfee78b!8m2!3d13.2924985!4d123.4903611!15sCgxSaHUgcG9sYW5ndWmSAQhob3NwaXRhbOABAA!16s%2Fg%2F11h_3ylltt?entry=ttu"))
                    startActivity(sideDrawerLocation)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Set the home menu item as selected
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.menu.findItem(R.id.bottomHome).isChecked = true

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.menu.findItem(R.id.sideHome).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
