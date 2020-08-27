package com.example.neostore.ProductDetails

import com.google.gson.annotations.SerializedName

data class Product_Data_response (

    @SerializedName("id") val id : Int,
    @SerializedName("product_category_id") val product_category_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("producer") val producer : String,
    @SerializedName("description") val description : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("rating") val rating : Int,
    @SerializedName("view_count") val view_count : Int,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String,
    @SerializedName("product_images") val product_images : List<Product_images_response>
)