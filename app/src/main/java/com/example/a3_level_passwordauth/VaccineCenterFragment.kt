package com.example.a3_level_passwordauth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_vaccine_centers.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VaccineCenterFragment : Fragment() {

    var lat: Double? = null
    var long: Double? = null
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.activity_vaccine_centers, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        super.setHasOptionsMenu(true)
        val sh = requireContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
        lat = Double.fromBits(sh.getLong("lat", 1))
        long = Double.fromBits(sh.getLong("long", 1))
        Log.i("OTP ACT ", "LAT R = " + lat)
        Log.i("OTP ACT ", "LONGR = " + long)


        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerlayout.layoutManager = linearLayoutManager

        getMyData()
    }

//

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cdn-api.co-vin.in/api/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(lat, long)
        Log.i("Vaccine", "retrofitData = " + retrofitData)
        retrofitData?.enqueue(object : Callback<Center> {
            override fun onResponse(
                call: Call<Center>,
                response: Response<Center>
            ) {

                val responseBody = response.body()!!
                Log.i("Vaccine", "responsebody = " + responseBody)
                myAdapter = MyAdapter(responseBody.centers)
                myAdapter.notifyDataSetChanged()
                recyclerlayout.adapter = myAdapter


            }

            override fun onFailure(call: Call<Center>, t: Throwable) {
                Log.i("Vaccine", "FAIL" + t.stackTrace)
            }

        })
    }}
