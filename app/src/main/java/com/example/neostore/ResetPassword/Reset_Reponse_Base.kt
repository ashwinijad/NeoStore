package com.example.neostore.ResetPassword

import com.google.gson.annotations.SerializedName

data class Reset_Reponse_Base
    (
    @SerializedName("status") val status : Int,
    @SerializedName("message") val message : String,
    @SerializedName("error") val error : Reset_Response_error,
    @SerializedName("user_msg") val user_msg : String
)
