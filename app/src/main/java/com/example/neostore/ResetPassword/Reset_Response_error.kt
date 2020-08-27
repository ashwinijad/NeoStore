package com.example.neostore.ResetPassword

import com.google.gson.annotations.SerializedName

data class Reset_Response_error (
    @SerializedName("confirm_password") val confirm_password : Reset_Response_Confirm_password

)