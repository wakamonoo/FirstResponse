package com.example.android.firstresponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

class SafetyAndPreparedeness : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safety_and_preparedness)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.shadow2)))

        getSupportActionBar()?.setTitle("SAFETY AND PREPAREDENESS");

        // calling the action bar
        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }


        //switching to cpr activity
        val buttonWater=findViewById<Button>(R.id.buttonWater)
        buttonWater.setOnClickListener{
            val Intent= Intent(this,WaterSafety::class.java)
            startActivity(Intent)
        }

        //switching to shock activity
        val buttonRoad=findViewById<Button>(R.id.buttonRoad)
        buttonRoad.setOnClickListener{
            val Intent= Intent(this,RoadSafety::class.java)
            startActivity(Intent)
        }

        //switching to splints activity
        val buttonDailyFood=findViewById<Button>(R.id.buttonDailyFood)
        buttonDailyFood.setOnClickListener{
            val Intent= Intent(this,DailyFoodSafety::class.java)
            startActivity(Intent)
        }

        //switching to choking activity
        val buttonEmergency=findViewById<Button>(R.id.buttonEmergency)
        buttonEmergency.setOnClickListener{
            val Intent= Intent(this,EmergencyFoodSafety::class.java)
            startActivity(Intent)
        }

        //switching to burns activity
        val buttonHeatwave=findViewById<Button>(R.id.buttonHeatwave)
        buttonHeatwave.setOnClickListener{
            val Intent= Intent(this,Heatwave::class.java)
            startActivity(Intent)
        }







    }

}