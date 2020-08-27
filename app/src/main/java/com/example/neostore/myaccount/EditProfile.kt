package com.example.neostore.myaccount

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.neostore.HomeActivity
import com.example.neostore.Login.LoginResponse
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.editprofile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EditProfile:AppCompatActivity (){
    private val IMAGE = 100
    var bitmap: Bitmap? = null
    var profile:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editprofile)
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
        val myCalendar: Calendar = Calendar.getInstance()

        val edittext1 = findViewById(R.id.dob) as EditText
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd-MM-yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                edittext1.setText(sdf.format(myCalendar.getTime()))      }

        edittext1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                DatePickerDialog(
                    this@EditProfile, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })
       val edit= edittext1.text
         profile = findViewById<View>(R.id.profilepic) as ImageView


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
                        Glide.with(applicationContext).load(retro1.profile_pic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(profile!!)
                        //image.setImageResource(retro1.profile_pic)
                    }
                    else{
                        try {
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            Toast.makeText(
                                applicationContext,
                             jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            Log.e("errorrr",e.message)
                        }
                    }
                }
            })




        profile?.setOnClickListener(View.OnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, IMAGE)
        })
        editsubmit.setOnClickListener {

            val first_name = firstname.text.toString().trim()
            val last_name = lastname.text.toString().trim()

            val email = emailregister.text.toString().trim()
            val phone = phoneno.text.toString().trim()

            val profile ="data:image/png;base64,"+convertToString()!!
            val token: String =
                SharedPrefManager.getInstance(
                    applicationContext
                ).user.access_token.toString()
        //    val description: RequestBody =
              //  createPartFromString("hello, this is description speaking")
            val first_name1 =
                RequestBody.create(MediaType.parse("text/plain"), first_name)
            val last_name1 =
                RequestBody.create(MediaType.parse("text/plain"), last_name)
            val email1 =
                RequestBody.create(MediaType.parse("text/plain"), email)
            val dob1 =
                RequestBody.create(MediaType.parse("text/plain"), edittext1.text.toString())
            val phone_no1 =
                RequestBody.create(MediaType.parse("text/plain"), phone)
            val profile_pic = RequestBody.create(
                MediaType.parse("text/plain"),
                profile
            )


            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("BitmapImage", profile)
            val map: MutableMap<String, RequestBody> = HashMap()

            map.put("first_name", first_name1);
            map.put("last_name", last_name1);
            map.put("email", email1);
            map.put("dob", dob1);
            map.put("phone_no", phone_no1);
            map.put("profile_pic", profile_pic);
            val requestFile: RequestBody =
                RequestBody.create(MediaType.parse("image/jpeg"), profile)

            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
            RetrofitClient.instance.useredit(token,map)
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
                            Toast.makeText(
                                applicationContext,
                                res.body()?.message,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("kjsfgxhufb",response.body()?.status.toString())
                        }
                        else
                        {
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
    private fun convertToString(): String? {
          val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val imgByte: ByteArray = byteArrayOutputStream.toByteArray()

        return android.util.Base64.encodeToString(imgByte, android.util.Base64.NO_WRAP )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val path: Uri? = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
                profile?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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