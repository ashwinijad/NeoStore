package com.example.neostore.ProductDetails

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.neostore.BuildConfig
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.product_details_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


class Product_details :AppCompatActivity (){
    private var rateValue = 0.0f

    var imagemain: ImageView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details_activity)
val descript:TextView=findViewById(R.id.descript)
        val intent1:Intent=getIntent()

        val id:String =intent1.getStringExtra("id")

        Log.e("getid", id)

        val text = findViewById<TextView>(R.id.text1) as TextView
        val text2=findViewById<TextView>(R.id.text2) as TextView
        val ratebutton=findViewById<Button>(R.id.rate)
        val buynow=findViewById<Button>(R.id.buynow)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver,
            IntentFilter("custom-message")
        )
 imagemain=findViewById<ImageView>(R.id.viewimage_main) as ImageView


        RetrofitClient.instancetable.fetchUserdetail(id)
            .enqueue(object : Callback<Product_base_response> {
                override fun onFailure(call: Call<Product_base_response>, t: Throwable) {
                    Log.d("res", "" + t)

                }

                override fun onResponse(
                    call: Call<Product_base_response>,
                    response: Response<Product_base_response>
                ) {
                    var res = response

                    val ret: Product_Data_response? = res.body()?.data
                    val retro: List<Product_images_response> = res.body()?.data?.product_images!!

                    Log.e("checkdata", ret?.name.toString())
                    val ygd = ret?.name.toString()
                    text.setText(ygd)
                    text2.setText(ret?.producer.toString())
                    descript.setText(ret?.description)

                    Glide.with(getApplicationContext())
                        .load(res?.body()!!.data.product_images.get(0).image).into(
                            imagemain!!
                        );

                    shareimage.setOnClickListener {
                        Glide.with(applicationContext).asBitmap().load(res?.body()!!.data.product_images.get(0).image)
                            .into(object : CustomTarget<Bitmap>() {

                                override fun onLoadCleared(placeholder: Drawable?) {

                                    // do your stuff, you can load placeholder image here
                                }

                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                                ) {


                                    val cachePath = File(applicationContext.cacheDir, "images")
                                    cachePath.mkdirs() // don't forget to make the directory
                                    val stream =
                                        FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                                    resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
                                    stream.close()

                                    val imagePath = File(applicationContext.cacheDir, "images")
                                    val newFile = File(imagePath, "image.png")
                                    val contentUri: Uri = FileProvider.getUriForFile(
                                        applicationContext,
                                        "${BuildConfig.APPLICATION_ID}.provider",
                                        newFile
                                    )

                                    val intent = Intent(Intent.ACTION_SEND)
                                    intent.type = "image/*"
                                    intent.putExtra(Intent.EXTRA_STREAM, contentUri)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                                    startActivity(
                                        Intent.createChooser(
                                            intent,
                                            "Choose..."
                                        )
                                    )

                                }
                            })

                    }


                    val bundle: Bundle = getIntent().getExtras()!!

                    val imgUrl: String = bundle.getString("image")!!
                    val imageUri = Uri.parse(imgUrl)

                    Log.e("imgUrl", imgUrl)
                    Log.e("imageUri", imageUri.toString())



                    val retro11: List<Product_images_response> =
                        response?.body()!!.data.product_images
                    generateDataList(retro11)
                    ratebutton.setOnClickListener {
                        val mBuild: AlertDialog.Builder = AlertDialog.Builder(this@Product_details)
                        val mView: View = layoutInflater.inflate(R.layout.ratedialog, null)

                        val ratebar = mView.findViewById(R.id.ratingBaruser) as RatingBar

                        val titleimage = mView.findViewById<TextView>(R.id.titleimage) as TextView
                        val imagerate = mView.findViewById<ImageView>(R.id.imagerate) as ImageView
                        titleimage.setText(ygd)
                        Glide.with(getApplicationContext()).load(
                            res?.body()!!.data.product_images.get(
                                0
                            ).image
                        ).into(imagerate);
                        val wmbPreference1 = PreferenceManager.getDefaultSharedPreferences(
                            applicationContext
                        )
                        val rating: Float = wmbPreference1.getFloat("numStars", 0f)
                        ratebar.setRating(rating)
                        ratebar.onRatingBarChangeListener =
                            OnRatingBarChangeListener { ratingBar, rating1, fromUser ->
                                val editor = wmbPreference1.edit();
                                editor.putFloat("numStars", rating1);
                                editor.commit();
                                rateValue = rating1
                                // Toast.makeText(this@Product_details, "" + rating1, Toast.LENGTH_SHORT).show()
                            }


                        mBuild.setView(mView)
                        val dialog: AlertDialog = mBuild.create()
                        //   btnSubmit.setOnClickListener(View.OnClickListener { dialog .dismiss() })

                        val btnSubmit =
                            mView.findViewById(R.id.btnSubRating) as Button
                        btnSubmit.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                RetrofitClient.instancetable.setRating(id, rateValue)
                                    .enqueue(object : Callback<Rate_Response> {
                                        override fun onFailure(
                                            call: Call<Rate_Response>,
                                            t: Throwable
                                        ) {
                                            Log.d("res", "" + t)
                                        }

                                        override fun onResponse(
                                            call: Call<Rate_Response>,
                                            response: Response<Rate_Response>
                                        ) {
                                            var res = response
                                            if (res.isSuccessful) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    res.body()?.user_msg,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                dialog.dismiss()
                                            } else {
                                                try {
                                                    val jObjError =
                                                        JSONObject(response.errorBody()!!.string())
                                                    Toast.makeText(
                                                        applicationContext,
                                                        jObjError.getString("message") + jObjError.getString(
                                                            "user_msg"
                                                        ),
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                } catch (e: Exception) {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        e.message,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    Log.e("errorrr", e.message)
                                                }
                                            }
                                        }
                                    })
                                Toast.makeText(
                                    this@Product_details,
                                    "" + rateValue,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                        dialog.show()
                    }
                    buynow.setOnClickListener {
                        val mBuild: AlertDialog.Builder = AlertDialog.Builder(this@Product_details)
                        val mView: View = layoutInflater.inflate(R.layout.buynowdialog, null)


                        val titleimage = mView.findViewById<TextView>(R.id.titleimage2) as TextView
                        val imagerate = mView.findViewById<ImageView>(R.id.imagebuy) as ImageView
                        titleimage.setText(ygd)
                        Glide.with(getApplicationContext()).load(
                            res?.body()!!.data.product_images.get(
                                0
                            ).image
                        ).into(imagerate);


                        val buynumber = mView.findViewById<EditText>(R.id.editbuy)

                        val btnSubmit =
                            mView.findViewById(R.id.btnbuynow) as Button
                        Log.e("checkid", id.toString())


                        mBuild.setView(mView)
                        val dialog: AlertDialog = mBuild.create()



                        btnSubmit.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                val value: String = buynumber.getText().toString()

                                if (value.isNotEmpty()) {
                                    val finalValue = value.toInt()
                                    val token: String =
                                        SharedPrefManager.getInstance(
                                            applicationContext
                                        ).user.access_token.toString()
                                    RetrofitClient.instancecart.buynow(token, id, finalValue)
                                        .enqueue(object : Callback<ResponseBaseCartAdd> {
                                            override fun onFailure(
                                                call: Call<ResponseBaseCartAdd>,
                                                t: Throwable
                                            ) {
                                                Log.d("res", "" + t)


                                            }

                                            override fun onResponse(
                                                call: Call<ResponseBaseCartAdd>,
                                                response: Response<ResponseBaseCartAdd>
                                            ) {
                                                Log.e("hi", id)
                                                var res = response
                                                Log.e("checkres", res.toString())
                                                Log.d(
                                                    "response check ",
                                                    "" + response.body()?.status.toString()
                                                )
                                                if (res.isSuccessful) {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        res.body()?.user_msg,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    dialog.dismiss()
                                                    Log.d(
                                                        "kjsfgxhufb",
                                                        response.body()?.user_msg.toString()
                                                    )
                                                } else {
                                                    try {
                                                        val jObjError =
                                                            JSONObject(
                                                                response.errorBody()!!.string()
                                                            )
                                                        Toast.makeText(
                                                            applicationContext,
                                                            jObjError.getString("message") + jObjError.getString(
                                                                "user_msg"
                                                            ),
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    } catch (e: Exception) {
                                                        Toast.makeText(
                                                            applicationContext,
                                                            e.message,
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                        Log.e("errorrr", e.message)
                                                    }
                                                }
                                            }
                                        })

                                } else {
                                    buynumber.error = "field is required"
                                    buynumber.requestFocus()
                                }

                            }
                        })
                        dialog.show()
                    }

                }
            })



    }


        fun generateDataList(dataList: List<Product_images_response>) {
            val recyclerView=findViewById<RecyclerView>(R.id.Recycleviewdetails)
            val adapter =Custom_detail_Adapter(applicationContext, dataList)
            recyclerView.adapter=adapter
            val linear:LinearLayoutManager=
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager=linear
            recyclerView.setHasFixedSize(true)
        }
    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent

            val ItemName = intent.getStringExtra("item")
            Glide.with(applicationContext).load(ItemName)
                .thumbnail(0.5f)

                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemain!!);
            shareimage.setOnClickListener {
                Glide.with(applicationContext).asBitmap().load(ItemName)
                    .into(object : CustomTarget<Bitmap>() {

                        override fun onLoadCleared(placeholder: Drawable?) {

                            // do your stuff, you can load placeholder image here
                        }

                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {


                            val cachePath = File(applicationContext.cacheDir, "images")
                            cachePath.mkdirs() // don't forget to make the directory
                            val stream =
                                FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                            resource.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            stream.close()

                            val imagePath = File(applicationContext.cacheDir, "images")
                            val newFile = File(imagePath, "image.png")
                            val contentUri: Uri = FileProvider.getUriForFile(
                                applicationContext,
                                "${BuildConfig.APPLICATION_ID}.provider",
                                newFile
                            )

                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "image/*"
                            intent.putExtra(Intent.EXTRA_STREAM, contentUri)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(
                                Intent.createChooser(
                                    intent,
                                    "Choose..."
                                )
                            )

                        }
                    })

            }

        }
    }

}