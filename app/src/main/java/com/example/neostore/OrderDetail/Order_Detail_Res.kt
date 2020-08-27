package com.example.neostore.OrderDetail

import com.google.gson.annotations.SerializedName

data class Order_Detail_Res (

    @SerializedName("id") val id : Int,
    @SerializedName("order_id") val order_id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("total") val total : Int,
    @SerializedName("prod_name") val prod_name : String,
    @SerializedName("prod_cat_name") val prod_cat_name : String,
    @SerializedName("prod_image") val prod_image : String
)