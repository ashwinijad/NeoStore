package com.example.neostore.myaccount

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.neostore.*
import com.example.neostore.ResetPassword.ResetPasswordActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myaccount)
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

val resetbutton=findViewById<Button>(R.id.resetpwd)
        resetbutton.setOnClickListener {
            val i=Intent(applicationContext,
                ResetPasswordActivity::class.java)
            startActivity(i)
        }
        val editbutton=findViewById<Button>(R.id.editdetail)
        editbutton.setOnClickListener {
            val i=Intent(applicationContext,EditProfile::class.java)
            startActivity(i)
        }

hello()
    }

    override fun onResume() {
        super.onResume()
        hello()
    }

    fun hello(){
        val first_name = findViewById<TextView>(R.id.firstname)
        val last_name = findViewById<TextView>(R.id.lastname)
        val emailuser = findViewById<TextView>(R.id.emailuser)
        val phone_no = findViewById<TextView>(R.id.phone_no)
        val birthday = findViewById<TextView>(R.id.birthday)
        val image=findViewById<ImageView>(R.id.imageprofile)
        val token :String =SharedPrefManager.getInstance(applicationContext).user.access_token.toString()
        RetrofitClient.instance.fetchUser(token)
            .enqueue(object : Callback<My_account_base_response> {
                override fun onFailure(call: Call<My_account_base_response>, t: Throwable) {

                    Log.d("res", "" + t)


                }

                override fun onResponse(
                    call: Call<My_account_base_response>,
                    response: Response<My_account_base_response>
                ) {
                    var res = response

                    if (res.body()?.status==200) {
                        //  val retro: List<Myaccount_data> = response.body().getData()

                        val retro: Myaccount_data = res.body()!!.data
                        val retro1 : User_data =retro.user_data
                        first_name.setText(retro1.first_name)
                        last_name.setText(retro1.last_name)
                        emailuser.setText(retro1.email)
                        phone_no.setText(retro1.phone_no).toString()
                        birthday.setText(retro1.dob).toString()
//image.setImageBitmap(decodedByte)
                        Glide.with(applicationContext).load(retro1.profile_pic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_launcher_foreground)

                            .into(image)
                        //image.setImageResource(retro1.profile_pic)
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}