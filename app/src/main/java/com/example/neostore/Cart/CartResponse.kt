package com.example.neostore.Cart

import com.google.gson.annotations.SerializedName

data class CartResponse (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : List<DataCart>,
    @SerializedName("count") val count : Int,
    @SerializedName("total") val total : Int
)
data class DataCart (

    @SerializedName("id") val id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("product") val product : ProductCart
)
data class ProductCart (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("product_category") val product_category : String,
    @SerializedName("product_images") val product_images : String,
    @SerializedName("sub_total") val sub_total : Int
)