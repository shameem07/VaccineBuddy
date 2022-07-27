package com.example.a3_level_passwordauth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient


class SplashScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            val Registered: Boolean
            val sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)

            Registered = sharedPreferences.getBoolean("Registered", false)

            if (!Registered) {
                startActivity(Intent(this, logins::class.java))
                finish()
            } else {
                startActivity(Intent(this, Home::class.java))
                finish()
            }
            finish()
        }, 1000)
    }
}


//