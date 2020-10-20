package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class Myaccountdata (

    @SerializedName("user_data") val user_data : Userdata,
    @SerializedName("product_categories") val product_categories : List<Productcategories>,
    @SerializedName("total_carts") val total_carts : Int,
    @SerializedName("total_orders") val total_orders : Int
)