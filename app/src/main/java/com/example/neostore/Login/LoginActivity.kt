package com.example.neostore.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neostore.*
import kotlinx.android.synthetic.main.login_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val button = findViewById<ImageView>(R.id.plusbutton)
        val forgotpassword=findViewById<TextView>(R.id.forgotpassword)
        button.setOnClickListener {
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }
        forgotpassword.setOnClickListener{
            val i = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(i)
        }
        loginbtn.setOnClickListener {
            // val i = Intent(applicationContext, HomeActivity::class.java)
            //startActivity(i)
            val email = loginuser.text.toString().trim()
            val password = loginpassword.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing",Toast.LENGTH_LONG
                ).show()
                loginuser.error = "Email required"
                loginuser.requestFocus()
                return@setOnClickListener
                        }


            if (password.isEmpty()) {
                loginpassword.error = "Password required"
                loginpassword.requestFocus()
                return@setOnClickListener
            }
            /*  val service: Api  = retrofitInstance.create<GetDataService>(
                  GetDataService::class.java
              )
              val call: Call<List<RetroPhoto>> = service.getAllPhotos()*/



            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("res", "" + t)


                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        var res = response

                        Log.d("response check ", "" + response.body()?.status.toString())
                        if (res.body()?.status==200) {
                         //   var res = response



                            SharedPrefManager.getInstance(applicationContext)
                                .saveUser(response.body()?.data!!)

                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            Toast.makeText(
                                applicationContext,
                                res.body()?.message,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("kjsfgxhufb",response.body()?.status.toString())
                            startActivity(intent)
                            finish()


                        }
             /*  else if(!email.isBlank() || !password.equals(""))
                        {
                            try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())
                                Toast.makeText(
                                    applicationContext,
                                    jObjError.getString("message")+jObjError.getString("user_msg"),
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e("missing",jObjError.getString("message")+jObjError.getString("user_msg"))
                            } catch (e: Exception) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                                Log.e("errorrr",e.message)
                            }
                        }*/

                else
                        {
                            try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())
                                Toast.makeText(
                                    applicationContext,
                                "hiii"+jObjError.getString("user_msg"),
                                    Toast.LENGTH_LONG
                                ).show()
                            } catch (e: Exception) {
                               Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                                Log.e("errorrr",e.message)
                            }
                        }




                      /*  else{
                            //Log.d("res", "" +  res.body()?.status.toString())

                            Toast.makeText(
                                applicationContext, (response.body()?.status.toString())
                                ,
                                Toast.LENGTH_LONG
                            ).show()
                            Toast.makeText(
                                applicationContext, "kjsh"
                                ,
                                Toast.LENGTH_LONG
                            ).show()

                            Log.d("edgerg", "" +  response.body()?.user_msg)
                            Log.d("edgerg", "" +  response.message())


                        }*******/
                        /*  else{
                              when (response.code()) {
                                  200 -> var res = response

                            Log.d("res", "" + res)

                            SharedPrefManager.getInstance(applicationContext)
                                .saveUser(response.body()?.data!!)

                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            Toast.makeText(
                                applicationContext,
                                res.body()?.message,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("kjsfgxhufb",res.body()?.message)
                            startActivity(intent)
                            finish()
                                  500 -> Toast.makeText(
                                      applicationContext,
                                      "server broken",
                                      Toast.LENGTH_SHORT
                                  ).show()
                                  else -> Toast.makeText(applicationContext,
                                      "unknown error",
                                      Toast.LENGTH_SHORT
                                  ).show()
                              }
                          }*/

                    }
                })

        }
    }

    /*  override fun onStart() {
          super.onStart()

          if(SharedPrefManager.getInstance(this).isLoggedIn){
              val intent = Intent(applicationContext, HomeActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

              startActivity(intent)
          }
      }*/
}

