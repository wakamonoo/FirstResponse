package com.example.android.firstaidanddisastermanagementapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_Layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setTitle("");



        //first aid
        val button1=findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            val Intent= Intent(this,FirstAid::class.java)
            startActivity(Intent)
        }

        //disaster_management
        val button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val Intent= Intent(this,DisasterManagement::class.java)
            startActivity(Intent)
        }

        //basic sanitation
        val button5=findViewById<Button>(R.id.button3)
        button5.setOnClickListener{
            val Intent= Intent(this,BasicSanitation::class.java)
            startActivity(Intent)
        }



        //bottom navigation bar functionality code
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.bottomHelpline -> {
                    // Handle click for "Helpline" item
                    dialPhoneNumber("8022268435")
                    true
                }

                else -> false
            }
        }

        //top navigation drawer  menu items functionality code
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.location-> {
                    val sideDrawerLocation = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/Rural+Health+Unit+-+Polangui/@13.2937115,123.4860495,17z/data=!4m10!1m2!2m1!1sRhu+polangui!3m6!1s0x33a1a1cd683fec01:0xd7b1601e3dfee78b!8m2!3d13.2924985!4d123.4903611!15sCgxSaHUgcG9sYW5ndWmSAQhob3NwaXRhbOABAA!16s%2Fg%2F11h_3ylltt?entry=ttu"))
                    startActivity(sideDrawerLocation)

                    true
                }




                else -> false
            }
        }



    }
//phone intent
    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

//when app is resumed after redirecting to web pages
    override fun onResume() {
        super.onResume()
        // Set the home menu item as selected
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.menu.findItem(R.id.bottomHome).isChecked = true

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.menu.findItem(R.id.sideHome).isChecked = true
    }



    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
