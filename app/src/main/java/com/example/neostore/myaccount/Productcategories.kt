package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class Productcategories (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("icon_image") val icon_image : String,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String
)