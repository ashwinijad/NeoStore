package com.example.neostore.Cart

import com.google.gson.annotations.SerializedName

data class DeleteResponse (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : Boolean,
    @SerializedName("total_carts") val total_carts : Int,
    @SerializedName("message") val message : String,
    @SerializedName("user_msg") val user_msg : String
)