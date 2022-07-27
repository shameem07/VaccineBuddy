package com.example.a3_level_passwordauth
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
//    @GET("v2/appointment/centers/public/findByLatLong")
//    fun getData(@Query("lat")latforap:Double?, @Query("long")longforap:Double?):Call<List<JSONArray>>


    @GET("v2/appointment/centers/public/findByLatLong")
    fun getData(
        @Query("lat") latforap:Double?,
        @Query("long") longforap:Double?
    ): Call<Center>
}