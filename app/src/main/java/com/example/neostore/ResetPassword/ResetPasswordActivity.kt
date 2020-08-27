package com.example.neostore.ResetPassword

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neostore.R
import com.example.neostore.ResetPassword.ErrorUtils.parseError
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resetpasswordlayout)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
            getSupportActionBar()?.setDisplayShowTitleEnabled(false);

            //supportActionBar?.setTitle("Tables")
            //    getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.Myaccount) + "</font>")));

        }
        var old_password = findViewById<EditText>(R.id.old_password)
        var new_password = findViewById<EditText>(R.id.new_password)
        var confirm_password =
            findViewById<EditText>(R.id.confirm_new_password)
        val resetpwdbutton = findViewById<Button>(R.id.resetpwdbtn)
        resetpwdbutton.setOnClickListener {

            val old = old_password.text.toString().trim()
            val new = new_password.text.toString().trim()
            val confirm = confirm_password.text.toString().trim()
            if (old.isEmpty()) {

                old_password.error = "old password is empty"
                old_password.requestFocus()
                return@setOnClickListener
            }


            if (new.isEmpty()) {
                new_password.error = "new password is empty"
                new_password.requestFocus()
                return@setOnClickListener
            }
            if (confirm.isEmpty()) {
                confirm_password.error = "confirm  password is empty"
                confirm_password.requestFocus()
                return@setOnClickListener
            }
          /*  if(new.length <= 5 && confirm.length<=5)
            {
                Toast.makeText(
                    applicationContext,
                    "password should more than 6 digits"
                    ,Toast.LENGTH_LONG

                ).show()                            }*/
           /* if(!new.equals(confirm))
            {
                Toast.makeText(
                    applicationContext, "password mismatched",Toast.LENGTH_LONG
                ).show()
            }*/
            val token: String =
                SharedPrefManager.getInstance(
                    applicationContext
                ).user.access_token.toString()
            RetrofitClient.instance.resetpassword(token, old, new, confirm)
                .enqueue(object : Callback<Reset_Reponse_Base> {
                    override fun onFailure(call: Call<Reset_Reponse_Base>, t: Throwable) {

                        Log.d("res", "" + t)


                    }

                    override fun onResponse(
                        call: Call<Reset_Reponse_Base>,
                        response: Response<Reset_Reponse_Base>
                    ) {
                        var res = response

                        if (res.body()?.status == 200) {

                            Log.d("response check ", "" + response.body()?.status.toString())
                            Toast.makeText(
                                applicationContext,
                                res.body()?.user_msg,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("kjsfgxhufb", response.body()?.user_msg.toString())
                          /*  if (!new.equals(confirm)) {
                                Toast.makeText(
                                    applicationContext, "password mismatched", Toast.LENGTH_LONG
                                ).show()
                            }*/

                        }
                        else{
                            val error: Reset_Reponse_Base = ErrorUtils.parseError(response)

                            if(new.length <= 5 && confirm.length<=5)
                            {
                                Toast.makeText(
                                    applicationContext,
                                    "password should more than 6 letters or digits"
                                    ,Toast.LENGTH_LONG

                                ).show()                            }
                            else{

                                Toast.makeText(
                                    applicationContext,
                                    error.error.confirm_password.compareWith,
                                    Toast.LENGTH_LONG

                                ).show()
                            }
                        }
                        /*else if ((res.code()==500)) {

                            //confused over here-->
                            //toast is not getting diaplayed when password and confirm password doesnt match
//val retr: Reset_Response_error? =res.body()?.error
  //                          val ret:Reset_Response_Confirm_password= retr?.confirm_password!!
                           /* try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())

                                Toast.makeText(
                                    applicationContext,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_LONG
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    applicationContext,
                                    e.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("errorrr", e.message)
                            }*/
                            val errorBody = response.errorBody().toString()
                            val json = JSONObject(errorBody)
                            Toast.makeText(
                                applicationContext,
                                json.getString("message"),
                                Toast.LENGTH_LONG
                            ).show()
                        }*/
                    }



                })
        }
    }
}
