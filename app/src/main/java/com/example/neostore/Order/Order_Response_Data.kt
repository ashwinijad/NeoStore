package com.example.neostore.Order

import com.google.gson.annotations.SerializedName

data class Order_Response_Data (

    @SerializedName("id") val id : Int,
    @SerializedName("cost") val cost : Int,
    @SerializedName("created") val created : String
)