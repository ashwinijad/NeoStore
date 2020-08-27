package com.example.neostore.ProductDetails

import com.google.gson.annotations.SerializedName

data class Rate_Response (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : DataRate,
    @SerializedName("message") val message : String,
    @SerializedName("user_msg") val user_msg : String
)
data class DataRate (

    @SerializedName("id") val id : Int,
    @SerializedName("product_category_id") val product_category_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("producer") val producer : String,
    @SerializedName("description") val description : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("rating") val rating : Double,
    @SerializedName("view_count") val view_count : Int,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String
)