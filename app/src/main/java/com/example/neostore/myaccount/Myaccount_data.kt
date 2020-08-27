package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class Myaccount_data (

    @SerializedName("user_data") val user_data : User_data,
    @SerializedName("product_categories") val product_categories : List<Product_categories>,
    @SerializedName("total_carts") val total_carts : Int,
    @SerializedName("total_orders") val total_orders : Int
)