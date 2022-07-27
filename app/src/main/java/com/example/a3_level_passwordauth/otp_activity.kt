package com.example.a3_level_passwordauth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class otp_activity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    private lateinit var dbRef: DatabaseReference
//    lateinit var dbe: String
    private lateinit var callbacks: OnVerificationStateChangedCallbacks
     var phns :String? = null
    var phone_number :String? =null
    var loginemail :String? = null
    var agefromdb :String? = null
    var namefromdb :String? = null
    var lat : Double? = null

    var long : Double? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        getLocation()
        auth = FirebaseAuth.getInstance()


        lateinit var storedVerificationId: String


//        val user = auth.currentUser
         loginemail = intent.getStringExtra("Email")

        dbRef = FirebaseDatabase.getInstance().reference.child("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {



                for (postSnapshot: DataSnapshot in dataSnapshot.children) {

                    val user: HashMap<String, String>? = postSnapshot.value as HashMap<String, String>?

                    val me = user?.get("email")
                    agefromdb = user?.get("agefromreg").toString()
                    namefromdb = user?.get("namefromreg").toString()


                    Log.i("OTP_Activity", "Email: " + me)

//
                    Log.i("OTP_Activity", "emailcopied: " + loginemail)

                    if (loginemail == me) {

                        if (loginemail != me) {
                            Toast.makeText(applicationContext, "not found", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.i("OTP_Activity", "Em: " + me)
                            val mp = user?.get("phone")
                            phns = mp

                            var phone_number = mp.toString()


                            Log.i("OTP_Activity", "Phone: " + phns)

                        }

                    }
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




        val otpsignin = sent_otp
        val verify = verify





        otpsignin.setOnClickListener {
            Handler().postDelayed(Runnable {
            otpsignin()
            }, 1000)
        }





        val num = FirebaseAuth.getInstance().currentUser!!.phoneNumber




        callbacks = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                Toast.makeText(applicationContext, "Success, OTP sent", Toast.LENGTH_SHORT).show()

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                var resendToken = token
            }

        }

        verify.setOnClickListener {
            val otp = given_otp.text.toString().trim()

            Log.d("OTP verification", "otp message - " + otp)
            if (otp.isEmpty()) {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
                given_otp.setError("Otp is Required")

            } else {
                Log.d("OTP verification", "otp message - " + otp)
                Log.d("OTP verification", "storedVerificationId - " + storedVerificationId)
                var credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)

            }
        }
    }
         fun otpsignin(){



            var number = phns




            if(!number!!.isEmpty()){
                number="+91"+number
                sendVerificationcode (number)
            }else {
                Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
            }
        }
        private  fun sendVerificationcode(number:String){
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)


        }
        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
//
                        getLocation()

                        val username: String = namefromdb.toString()
                        val email: String = loginemail.toString()
                        val age: String =agefromdb.toString()
                        val phone:String = phone_number.toString()




// Creating an Editor object to edit(write to the file)


                        Log.i("OTP ACT ","LAT= "+lat)
                        Log.i("OTP ACT ","LONG= "+long)
                        val sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
                        val myEdit = sharedPreferences.edit()

                        myEdit.putBoolean("Registered", true)
                        myEdit.putString("Username", username)
                        myEdit.putString("Email", email)
                        myEdit.putString("Age", age)
                        myEdit.putString("Phone", phns)
                        myEdit.commit()

                        startActivity(Intent(applicationContext,Home::class.java))

                            finish()


                    }else {
                        if(task.exception is FirebaseAuthInvalidCredentialsException){
                            Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                        }
                    }


                }

        }
    private fun getLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {


                }
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        lat = list[0].latitude
                        long = list[0].longitude
                        val sharedPreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
                        val myEdit = sharedPreferences.edit()
//                     test
                        myEdit.putLong("lat",lat!!.toBits())
                        myEdit.putLong("long",long!!.toBits())
                        myEdit.commit()

//
                            Log.i("Vaccine", "Latsnt" + lat)
                            Log.i("Vaccine", "longsnt" + long)

//
//
                    }
                }

            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)

            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {

        val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }



    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }



}







