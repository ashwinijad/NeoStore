package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class User_data (

    @SerializedName("id") val id : Int,
    @SerializedName("role_id") val role_id : Int,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("email") val email : String,
    @SerializedName("username") val username : String,
    @SerializedName("profile_pic") val profile_pic : String,
    @SerializedName("country_id") val country_id : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("phone_no") val phone_no : String,///changed to String but it is Int
    @SerializedName("dob") val dob : String,
    @SerializedName("is_active") val is_active : Boolean,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String,
    @SerializedName("access_token") val access_token : String
)