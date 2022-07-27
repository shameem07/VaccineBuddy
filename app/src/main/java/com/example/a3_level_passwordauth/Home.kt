package com.example.a3_level_passwordauth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_vaccine_centers.*


class Home : AppCompatActivity() {
    var backPressedTime: Long = 0
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content,VaccineCenterFragment(),"VaccineCenterFragment")
//            .addToBackStack("VaccineCenterFragment")
            .commit()




        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu_main,menu)
//        inflater.inflate(R.menu.menu_main,menu)
//        super.onCreateOptionsMenu(menu, inflater)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> finishAffinity()
            R.id.logout -> logout()
            R.id.profile -> profile()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun profile() {
        Log.i("VCF","profile option selected")

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content,UserDetailsFragment(),"UserDetailsFragment")
            .addToBackStack("UserDetailsFragment")
            .commit()

    }
    private fun logout() {
        val sharedPreferences = this.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.commit()
        val fm: FragmentManager =getSupportFragmentManager()
        for (i in 0 until fm.getBackStackEntryCount()) {
            fm.popBackStack()
        }

        val i = Intent(this, logins::class.java)
        startActivity(i)
        finish()
    }

}

//