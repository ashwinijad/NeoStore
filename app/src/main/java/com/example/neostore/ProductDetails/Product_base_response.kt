package com.example.neostore.ProductDetails

import com.google.gson.annotations.SerializedName

data class Product_base_response (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : Product_Data_response
)