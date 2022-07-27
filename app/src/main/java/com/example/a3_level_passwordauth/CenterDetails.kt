package com.example.a3_level_passwordauth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a3_level_passwordauth.R
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_vaccine_centers.*
import kotlinx.android.synthetic.main.fragment_center_details.*


class CenterDetails : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_center_details, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         val sh = requireContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        val centername = sh.getString("centername","")
        val centerid = sh.getString("centerid","")
        val addressf = sh.getString("address","")



        val namedisplay = requireActivity().findViewById(R.id.centernameforlocate) as TextView
        val iddisplay = requireActivity().findViewById(R.id.centeridforlocate) as TextView
        val addressfordisplay = requireActivity().findViewById(R.id.addressforlocate) as TextView
        namedisplay.text = "Center Name: $centername"
        iddisplay.text = "Center ID: $centerid"
        addressfordisplay.text = "Address: $addressf"

        btnforlocate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_content,MapFragment())
                .addToBackStack("MAP FRAGMENT")
                .commit()
        }
    }

}
