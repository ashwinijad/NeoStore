package com.example.neostore.Order

import com.google.gson.annotations.SerializedName


data class Order_Response_Base (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : List<Order_Response_Data>,
    @SerializedName("message") val message : String,
    @SerializedName("user_msg") val user_msg : String
)
