package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class My_account_base_response (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : Myaccount_data
)