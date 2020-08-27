package com.example.neostore.OrderDetail

import com.google.gson.annotations.SerializedName

data class Order_Detail_Data (

    @SerializedName("id") val id : Int,
    @SerializedName("cost") val cost : Int,
    @SerializedName("address") val address : String,
    @SerializedName("order_details") val order_details : List<Order_Detail_Res>
)