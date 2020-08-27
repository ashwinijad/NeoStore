package com.example.neostore.ProductDetails

import com.google.gson.annotations.SerializedName

data class Product_images_response (

    @SerializedName("id") val id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("image") val image : String,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String
)