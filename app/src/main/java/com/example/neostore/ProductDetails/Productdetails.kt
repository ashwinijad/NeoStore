package com.example.neostore.ProductDetails

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.neostore.BuildConfig
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.HomeActivity
import com.example.neostore.ProductDetails.RoomProdDB.FavProdDB
import com.example.neostore.R
import com.example.neostore.SharedPrefManager
import com.example.neostore.table.Tabledata
import kotlinx.android.synthetic.main.product_details_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors
import android.os.Handler
import com.example.neostore.BaseClassActivity

class Productdetails : BaseClassActivity(){
    private var rateValue = 0.0f
    companion object
    {
        var favoriteDatabase: FavProdDB? = null

    }

    val NUM_OF_THREADS = 4
    var executorService = Executors.newFixedThreadPool(NUM_OF_THREADS)
    var viewPager: ViewPager? = null

    var imagemain: ImageView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details_activity)


        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);


  val descript:TextView=findViewById(R.id.descript)
        val intent1:Intent=getIntent()

        val id:Int =intent1.getIntExtra("id", -1)
        val product_category_id:Int =intent1.getIntExtra("product_category_id", -1)

        val name:String =intent1.getStringExtra("name")
        setScreenTitle(name)

        val producer:String =intent1.getStringExtra("producer")
        val description:String =intent1.getStringExtra("description")
        val cost:Int =intent1.getIntExtra("cost", -1)
        val rating:String =intent1.getStringExtra("rating")
        val view_count:Int =intent1.getIntExtra("view_count", -1)
        val created:String =intent1.getStringExtra("created")
        val modified:String =intent1.getStringExtra("modified")
       val product_images:String =intent1.getStringExtra("image")
        Log.e("product_images", product_images)
Log.e("sdjhj", product_images)
        Log.e("checkname", name)
        val retroPhoto = Tabledata(id,
            product_category_id,
            name,
            producer,
            description,
            cost,
            rating,
            view_count,
            created,
            modified,
            product_images)

        fav.setOnClickListener(View.OnClickListener {
            if (favoriteDatabase?.favoriteDao()
                    ?.isFavorite(id) !== 1
            ) {
                fav.setImageResource(R.drawable.favourite_red)
                HomeActivity.favoriteDatabase?.favoriteDao()?.addData(retroPhoto)

            } else {
                fav.setImageResource(R.drawable.favourite_null)
                HomeActivity.favoriteDatabase!!.favoriteDao().delete(retroPhoto)
            }
        })

        if (favoriteDatabase?.favoriteDao()
                ?.isFavorite(id) === 1
        ) fav.setImageResource(R.drawable.favourite_red) else fav.setImageResource(
            R.drawable.favourite_null)




     //   database = FavoriteRoomDatabase.getDatabase(this)


   //     addFavorites()
   //     addViewPager()
   //     fav.setOnClickListener(View.OnClickListener { updateFavorites() })

        val text = findViewById<TextView>(R.id.text1) as TextView
        val text2=findViewById<TextView>(R.id.text2) as TextView
        val ratebutton=findViewById<Button>(R.id.rate)
        val buynow=findViewById<Button>(R.id.buynow)




        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver,
            IntentFilter("custom-message")
        )




 imagemain=findViewById<ImageView>(R.id.viewimage_main) as ImageView


        RetrofitClient.instance.fetchUserdetail(id)
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
                    textcost.setText(ret?.cost.toString())
                    text2.setText(ret?.producer.toString())
                    descript.setText(ret?.description)
                    myRatingBar.rating = rating.toFloat()
                    Glide.with(getApplicationContext())
                        .load(res?.body()!!.data.product_images.get(0).image).into(
                            imagemain!!
                        );
                    download.setOnClickListener {

                        /*      //Download Script
                            val downloadManager: DownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                            val uri = Uri.parse(product_images)
                            val request: DownloadManager.Request = DownloadManager.Request(uri)
                            request.setVisibleInDownloadsUi(true)
                            Toast.makeText(
                                applicationContext,
                               uri.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                uri.lastPathSegment)
                            downloadManager.enqueue(request)


                       */
                        val request = DownloadManager.Request(
                            Uri.parse(product_images))
                        request.allowScanningByMediaScanner()
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                        FileUtilss.createDirectory(AppConstants.DOWNLOADS_FOLDER)
                        val imageFile: File =
                            File(AppConstants.DOWNLOADS_FOLDER.toString() + "Neostore_" + id + ".jpg")
                        if (imageFile.exists()) {
                          showToast(applicationContext,"Image is Already downloaded")
                        } else {
                            request.setDestinationUri(Uri.fromFile(imageFile))
                            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                            dm.enqueue(request)
                            showToast(applicationContext,"Downloading File")
                        }
                    }

                    shareimage.setOnClickListener {
                        Glide.with(applicationContext).asBitmap()
                            .load(res?.body()!!.data.product_images.get(
                                0).image)
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
                        ratebutton.setBackgroundResource(R.drawable.mybuttonred)
                        // change to original after 5 secs.
                        Handler().postDelayed(Runnable { ratebutton.setBackgroundResource(R.drawable.mybutton)
                        },
                            1000)
                        val mBuild: AlertDialog.Builder = AlertDialog.Builder(this@Productdetails)
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
                       // val wmbPreference1 = PreferenceManager.getDefaultSharedPreferences(
                       //     applicationContext
                       // )
                       // val rating: Float = wmbPreference1.getFloat("numStars", 0f)
                        ratebar.setRating(getDefaults("numStars",applicationContext))
                        ratebar.onRatingBarChangeListener =
                            OnRatingBarChangeListener { ratingBar, rating1, fromUser ->
                              //  val editor = wmbPreference1.edit();
                              //  editor.putFloat("numStars", rating1);
                              //  editor.commit();
                                setDefaults("numStars",rating1,applicationContext)
                                rateValue = rating1
                            }


                        mBuild.setView(mView)
                        val dialog: AlertDialog = mBuild.create()
                        //   btnSubmit.setOnClickListener(View.OnClickListener { dialog .dismiss() })

                        val btnSubmit =
                            mView.findViewById(R.id.btnSubRating) as Button
                        btnSubmit.setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                RetrofitClient.instance.setRating(id, rateValue)
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
                                               showToast(applicationContext,res.body()?.user_msg)
                                                dialog.dismiss()
                                            } else {
                                                try {
                                                    val jObjError =
                                                        JSONObject(response.errorBody()!!.string())
                                                   showToast(applicationContext,jObjError.getString(
                                                       "user_msg"
                                                   ))
                                                } catch (e: Exception) {
                                                   showToast(applicationContext,e.message)
                                                    Log.e("errorrr", e.message)
                                                }
                                            }
                                        }
                                    })

                            }
                        })
                        dialog.show()
                        //  dialog.getWindow()?.setLayout(1000, 1500);
                        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));


                    }
                    buynow.setOnClickListener {
                        buynow.setBackgroundResource(R.drawable.mybuttonred)
                        // change to original after 5 secs.
                        Handler().postDelayed(Runnable { buynow.setBackgroundResource(R.drawable.mybutton)
                        },
                            1000)

                        val mBuild: AlertDialog.Builder = AlertDialog.Builder(this@Productdetails)
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
                                    RetrofitClient.instance.buynow(token, id, finalValue)
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
                                                var res = response
                                                Log.e("checkres", res.toString())
                                                Log.d(
                                                    "response check ",
                                                    "" + response.body()?.status.toString()
                                                )
                                                if (res.isSuccessful) {
                                                 showToast(applicationContext, res.body()?.user_msg)
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
                                                       showToast(applicationContext,jObjError.getString(
                                                           "user_msg"
                                                       ))
                                                    } catch (e: Exception) {
                                                        showToast(applicationContext,e.message)
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
                        //   dialog.getWindow()?.setLayout(1000, 1700);
                        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}