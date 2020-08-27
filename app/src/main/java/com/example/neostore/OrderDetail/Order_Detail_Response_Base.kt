package com.example.neostore.OrderDetail

import com.google.gson.annotations.SerializedName

data class Order_Detail_Response_Base (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : Order_Detail_Data
)