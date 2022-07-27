package com.example.a3_level_passwordauth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_vaccine_centers.*
import kotlinx.android.synthetic.main.userdetailsfragment.*


/**
 * A simple [Fragment] subclass.
 */
class UserDetailsFragment : Fragment() {
    var backPressedTime:Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.userdetailsfragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sh  = requireContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        val emailfromsh = sh.getString("Email","")
        val namefromsh = sh.getString("Username","")
        val phonefromsh = sh.getString("Phone","")
        val agefromsh = sh.getString("Age","")
        val emailview = view.findViewById<TextView>(R.id.emailview)
        val phoneview = view.findViewById<TextView>(R.id.phoneview)


            emailview.text = emailfromsh
            phoneview.text = phonefromsh
            nameview.text = namefromsh
            ageview.text = agefromsh

    }
    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()

    }



    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onLowMemory() {
        super.onLowMemory()

    }


}
