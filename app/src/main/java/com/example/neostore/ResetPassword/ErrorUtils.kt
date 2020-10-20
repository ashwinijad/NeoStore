package com.example.neostore.ResetPassword

import com.example.neostore.ClientApi.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


object ErrorUtils {
    fun parseError(response: Response<*>): Reset_Reponse_Base {
        val converter: Converter<ResponseBody, Reset_Reponse_Base> = RetrofitClient.retrofit1
            .responseBodyConverter(Reset_Reponse_Base::class.java, arrayOfNulls<Annotation>(0))
        val error: Reset_Reponse_Base
        error = try {
            converter.convert(response.errorBody())
        } catch (e: IOException) {
            return Reset_Reponse_Base(status = 500,message = "",error = Reset_Response_error(
                Reset_Response_Confirm_password("")
            ),user_msg = "")
        }
        return error
    }
}