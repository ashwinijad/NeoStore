package com.example.neostore

import com.google.gson.annotations.SerializedName

data class ForgotResponse (

    @SerializedName("status") val status : Int,
    @SerializedName("message") val message : String,
    @SerializedName("user_msg") val user_msg : String


)
