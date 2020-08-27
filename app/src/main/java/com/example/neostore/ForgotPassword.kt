package com.example.neostore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neostore.Login.LoginActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgotpassword)

        var forgotemailedt =findViewById<EditText>(R.id.forgotemailedt)
        var sendmailforgot =findViewById<Button>(R.id.sendmailforgot)


        sendmailforgot.setOnClickListener {
            // val i = Intent(applicationContext, HomeActivity::class.java)
            //startActivity(i)
            val email = forgotemailedt.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing",Toast.LENGTH_LONG
                ).show()
                forgotemailedt.error = "Email required"
                forgotemailedt.requestFocus()
                return@setOnClickListener
            }



            RetrofitClient.instance.emailForgotpwd(email)
                .enqueue(object : Callback<ForgotResponse> {
                    override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {
                        Log.d("res", "" + t)


                    }

                    override fun onResponse(
                        call: Call<ForgotResponse>,
                        response: Response<ForgotResponse>
                    ) {
                        var res = response

                        Log.d("response check ", "" + response.body()?.status.toString())
                        if (res.body()?.status==200) {
                            Toast.makeText(
                                applicationContext,
                                res.body()?.user_msg,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("kjsfgxhufb",response.body()?.user_msg.toString())
                        }
                        else{
                            try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())
                                Toast.makeText(
                                    applicationContext,
                                    jObjError.getString("message")+jObjError.getString("user_msg"),
                                    Toast.LENGTH_LONG
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                                Log.e("errorrr",e.message)
                            }
                        }
                    }
                })

        }



    }
    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 2)
        super.onBackPressed()
    }
}