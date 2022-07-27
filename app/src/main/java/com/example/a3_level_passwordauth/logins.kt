package com.example.a3_level_passwordauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class logins : AppCompatActivity()  {


    lateinit var auth: FirebaseAuth


    var tvRedirectLogin: TextView? = null
    var isAllFieldsChecked: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//

        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)


        tvRedirectLogin?.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }


        auth = FirebaseAuth.getInstance()


        signin.setOnClickListener {
            isAllFieldsChecked = checkallfields()
            if(isAllFieldsChecked == true){
            login()

        }



        }

    }

    private fun checkallfields(): Boolean {
        if (login_email.text.toString().isBlank())  {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT).show()
            login_email.setError("Email is Blank")
            return false
        }
        if (login_Password.text.toString().isBlank()){
            login_Password.setError("Password is Blank")
            return false
        }
        return true
    }

    private fun login() {
        val email = login_email.text.toString()
        val pass = login_Password.text.toString()

        auth.signInWithEmailAndPassword(email ,pass).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Successfully LoggedIn",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,otp_activity::class.java)
                val  emailsnt = email
                Log.i("Login_Activity", "emailcopied: " + emailsnt)

                intent.putExtra("Email",email)

                    startActivity(intent)
                        finish()

                } else
               Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
        }
        }
//




}




