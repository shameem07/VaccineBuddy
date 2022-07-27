package com.example.a3_level_passwordauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class Registration : AppCompatActivity() {
    //    var etEmail: EditText? = null
//    var etcpassword: EditText? = null
//    private  var etpassword: EditText? = null
//    private  var signup : Button? = null
    private var auth: FirebaseAuth? = null
    private lateinit var dbRef: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


//        etEmail = findViewById(R.id.etEmail)
//        etpassword = findViewById(R.id.etPassword)
//        signup = findViewById(R.id.signup)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val emailsf = etEmail.text.toString()
        auth = FirebaseAuth.getInstance()
        signup?.setOnClickListener {
            if(etPassword.length()<6)
                etPassword.setError("Password should contain minimum of 6 Characters")

//            else (android.util.Patterns.EMAIL_ADDRESS.matcher(emailsf).matches())

                signupUser()
//            else etEmail.setError("Email is Not Valid")


        }


    }

    private fun signupUser() {
        val namef = namefromreg.text.toString()
        val agef = agefromreg.text.toString()
        val emailf = etEmail.text.toString()
        val passf = etPassword.text.toString()
        val ConfirmPasword = etcpassword.text.toString()
        val phf = etphone.text.toString()




        if (emailf.isBlank())  {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT).show()
            etEmail.setError("Email is Blank")
            return
        }
        if (passf.isBlank()){
            etPassword.setError("Password is Blank")
            return
        }
        if(ConfirmPasword.isBlank()){
            etcpassword.setError("Confirm Password is Blank")
            return
        }
        if(phf.isBlank()){
            etphone.setError("Phone Number is Required")
            return
        }
        if (passf != ConfirmPasword) {
            Toast.makeText(this,"Password and Confirm Password do not match",Toast.LENGTH_SHORT).show()
                etcpassword.setError("Password and Confirm Password do not match")
            return
        }
        auth?.createUserWithEmailAndPassword(emailf, passf)?.addOnCompleteListener(this) {
            if (it.isSuccessful) {

                saveuser(namef,agef,emailf,passf,phf)

                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, logins::class.java)
                startActivity(intent)


            } else {
                Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveuser(namef:String,agef:String,emailf:String,passf:String,phf:String) {

        val db = dbRef.push().key!!

        val user = User(namef,agef,emailf, passf, phf)

        dbRef.child(db).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data Insert failed", Toast.LENGTH_LONG).show()
            }


    }
}





