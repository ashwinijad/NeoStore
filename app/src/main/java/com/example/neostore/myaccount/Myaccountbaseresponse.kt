package com.example.neostore.myaccount

import com.google.gson.annotations.SerializedName

data class Myaccountbaseresponse (

    @SerializedName("status") val status : Int,
    @SerializedName("data") val data : Myaccountdata
)