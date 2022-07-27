package com.example.a3_level_passwordauth

data class Center(
    val centers: List<CenterX>,
    val ttl: Int
)

data class CenterX(
    val block_name: String,
    val center_id: Int,
    val district_name: String,
    val lat: String,
    val location: String,
    val long: String,
    val name: String,
    val pincode: String,
    val state_name: String
)







//data class Center(
//    val block_name: String,
//    val center_id: Int,
//    val district_name: String,
//    val lat: String,
//    val location: String,
//    val long: String,
//    val name: String,
//    val pincode: String,
//    val state_name: String
//)