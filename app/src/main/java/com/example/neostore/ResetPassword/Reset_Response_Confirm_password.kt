package com.example.neostore.ResetPassword

import com.google.gson.annotations.SerializedName

data class Reset_Response_Confirm_password (
    @SerializedName("compareWith") val compareWith : String
)