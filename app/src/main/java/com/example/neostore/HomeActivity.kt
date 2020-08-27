package com.example.neostore

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.neostore.Cart.AddToCart
import com.example.neostore.Cart.CartResponse
import com.example.neostore.Cart.DataCart
import com.example.neostore.Login.LoginActivity
import com.example.neostore.Order.OrderListActivity
import com.example.neostore.chair.Chairs_Home
import com.example.neostore.cupboards.Cupboards_Home
import com.example.neostore.myaccount.MyAccount
import com.example.neostore.myaccount.My_account_base_response
import com.example.neostore.myaccount.Myaccount_data
import com.example.neostore.myaccount.User_data
import com.example.neostore.sofas.Sofa_Home
import com.example.neostore.table.Tables
import com.google.android.material.navigation.NavigationView
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.home_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener  {
    private var mPager: ViewPager? = null
    private var currentPage = 0
    private var NUM_PAGES = 0
    private val END_SCALE = 0.7f
    var Tablelist : List<Data> = listOf()
    private lateinit var counterView: TextView

    private val IMAGES =
        arrayOf<Int>(
            R.drawable.slider_img1,
            R.drawable.slider_img2,
            R.drawable.slider_img3,
            R.drawable.slider_img4
        )
    var toolbar: Toolbar? = null
    var navigationView: NavigationView? = null
    var holderView: View? = null
    private val ImagesArray = ArrayList<Int>()
    private var isLastPageSwiped = false
    private var counterPageScroll = 0
    var drawer: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
image1.setOnClickListener{
    val i = Intent(applicationContext, Tables::class.java)
    startActivity(i)
}
        image2.setOnClickListener{
            val i = Intent(applicationContext, Sofa_Home::class.java)
            startActivity(i)
        }
        image3.setOnClickListener{
            val i = Intent(applicationContext, Chairs_Home::class.java)
            startActivity(i)
        }
        image4.setOnClickListener {
            val i = Intent(applicationContext, Cupboards_Home::class.java)
            startActivity(i)
        }

        init()
        toolbar =
            findViewById<View>(R.id.toolbar) as Toolbar
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val hView = navigationView!!.getHeaderView(0)
        val nav_user = hView.findViewById<View>(R.id.nav_header_textView) as TextView
        val nav_email=hView.findViewById<View>(R.id.text_nav_2) as TextView
        val nav_header_imagehg=hView.findViewById<View>(R.id.nav_header_imageView)
        val nav_user_text :String =SharedPrefManager.getInstance(applicationContext).user.first_name.toString()+" " +SharedPrefManager.getInstance(
            applicationContext
        ).user.last_name.toString()
        val nav_user_email :String =SharedPrefManager.getInstance(applicationContext).user.email.toString()
        nav_user.setText(nav_user_text)
        nav_email.setText(nav_user_email)


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

                    if (res.body()?.status == 200) {
                        //  val retro: List<Myaccount_data> = response.body().getData()

                        val retro: Myaccount_data = res.body()!!.data
                        val retro1: User_data = retro.user_data

//image.setImageBitmap(decodedByte)
                        Glide.with(applicationContext).load(retro1.profile_pic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_launcher_foreground)

                            .into(hView.findViewById(R.id.nav_header_imageView))
                        //image.setImageResource(retro1.profile_pic)
                    } else {
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
                            Log.e("errorrr", e.message)
                        }
                    }
                }
            })



//navigationView

        holderView = findViewById(R.id.holder)
        var contentView:View?=null
        contentView=findViewById(R.id.content)
        setSupportActionBar(toolbar)
        val t =
            ActionBarDrawerToggle(
                this,
                drawer,
                R.string.Open,
                R.string.Close
            )
        t.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawer!!.addDrawerListener(t)
        t.syncState()
        t.isDrawerIndicatorEnabled = true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationOnClickListener {
            if (drawer!!.isDrawerOpen(navigationView!!)) {
                drawer!!.closeDrawer(navigationView!!)
            } else {
                drawer!!.openDrawer(navigationView!!)

            }
        }
        drawer!!.setScrimColor(Color.TRANSPARENT)
        drawer!!.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(
                drawer: View,
                slideOffset: Float
            ) {


                val diffScaledOffset: Float = slideOffset * (1 - END_SCALE);
                val offsetScale: Float = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                val xOffset: Float = drawer.getWidth() * slideOffset;
                val xOffsetDiff: Float = contentView.getWidth() * diffScaledOffset / 2;
                val xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }

            override fun onDrawerClosed(drawerView: View) {}
        }
        )
navigationView!!.setNavigationItemSelectedListener(this)
    //    val txtProfileName: TextView =
  //          navigationView!!.getHeaderView(0).findViewById<View>(R.id.nav_header_textView) as TextView
//        txtProfileName.setText(Tablelist.get(0).first_name)

        counterView = navigationView!!.menu.findItem(R.id.MyCart).actionView.findViewById(R.id.counter)

        RetrofitClient.instancecart.listcart(token).enqueue( object :
            Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {

                val res=response
                if (response.isSuccessful) {

                    val retro: String = response.body()!!.count.toString()
                    if(retro.contains("0")){

                        counterView.setVisibility(View.GONE);

                    }else{
                        counterView.setVisibility(View.VISIBLE)
                        counterView.setText(retro)

                    }

                }
            }

        })
    }



    override fun onResume() {
        super.onResume()
        // put your code here...

        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val hView = navigationView!!.getHeaderView(0)
        val nav_user = hView.findViewById<View>(R.id.nav_header_textView) as TextView
        val nav_email=hView.findViewById<View>(R.id.text_nav_2) as TextView
        val nav_header_imagehg=hView.findViewById<View>(R.id.nav_header_imageView)
       // val nav_user_text :String =SharedPrefManager.getInstance(applicationContext).user.first_name.toString()+" " +SharedPrefManager.getInstance(applicationContext).user.last_name.toString()
      //  val nav_user_email :String =SharedPrefManager.getInstance(applicationContext).user.email.toString()
      //  nav_user.setText(nav_user_text)
      //  nav_email.setText(nav_user_email)


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

                    if (res.body()?.status == 200) {
                        //  val retro: List<Myaccount_data> = response.body().getData()

                        val retro: Myaccount_data = res.body()!!.data
                        val retro1: User_data = retro.user_data
                        nav_user.setText(retro1.first_name + " " + retro1.last_name)
                        nav_email.setText(retro1.email)
//image.setImageBitmap(decodedByte)
                        Glide.with(applicationContext).load(retro1.profile_pic)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_launcher_foreground)

                            .into(hView.findViewById(R.id.nav_header_imageView))
                        //image.setImageResource(retro1.profile_pic)
                    } else {
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
                            Log.e("errorrr", e.message)
                        }
                    }
                }
            })
        counterView = navigationView!!.menu.findItem(R.id.MyCart).actionView.findViewById(R.id.counter)

        RetrofitClient.instancecart.listcart(token).enqueue( object :
            Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {

                val res=response
                if (response.isSuccessful) {

                    val retro: String = response.body()!!.count.toString()

                    if(retro.contains("0")){

                        counterView.setVisibility(View.GONE);

                    }else{
                        counterView.setVisibility(View.VISIBLE)
                        counterView.setText(retro)

                    }
                }
            }

        })



    }
    private fun init() {
        for (i in IMAGES.indices) ImagesArray.add(IMAGES[i])
        mPager = findViewById(R.id.viewpagerhome) as ViewPager?
        mPager!!.adapter = SliderImager_Adapter(this@HomeActivity, ImagesArray)
        val indicator = findViewById(R.id.indicator) as CirclePageIndicator
        indicator.setViewPager(mPager)

        val density: Float = getResources().getDisplayMetrics().density

//Set circle indicator radius
        indicator.radius = 5 * density
        NUM_PAGES = IMAGES.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)

            }
        }, 1000, 2000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                //    currentPage = position
                if (position == 3 && !isLastPageSwiped) {
                    if (counterPageScroll != 0) {
                        isLastPageSwiped = true;
                        //Next Activity here
                    }
                    counterPageScroll++;
                } else {
                    counterPageScroll = 0;
                }
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        var pager: ViewPager? = null
    }

    override fun onStart() {
        super.onStart()

        if(!(SharedPrefManager.getInstance(this).isLoggedIn)){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        p0.setChecked(true);
        drawer?.closeDrawers();
        when (p0.itemId) {
            R.id.MyCart -> {

                val i = Intent(applicationContext, AddToCart::class.java)
                startActivity(i)
            }
            R.id.Tables -> {
                val i = Intent(applicationContext, Tables::class.java)
                startActivity(i)

            }
            R.id.Sofas -> {
                val i = Intent(applicationContext, Sofa_Home::class.java)
                startActivity(i)
            }
            R.id.Chairs -> {
                val i = Intent(applicationContext, Chairs_Home::class.java)
                startActivity(i)
            }
            R.id.CupBoard -> {
                val i = Intent(applicationContext, Cupboards_Home::class.java)
                startActivity(i)
            }
            R.id.myaccount -> {

                val i = Intent(applicationContext, MyAccount::class.java)
                startActivity(i)
            }
            R.id.storelocator ->
                Toast.makeText(this, "Store Locator", Toast.LENGTH_SHORT).show()
            R.id.myorders -> {
                val i = Intent(applicationContext, OrderListActivity::class.java)
                startActivity(i)
            }
            R.id.logout -> {
                logout()
            }
        }
        drawer?.closeDrawer(GravityCompat.START);
        return true;
    }
    private fun logout(){
        SharedPrefManager.getInstance(application).clear()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

    }

}
