package com.example.neostore.Cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.Address.AddressActivity
import com.example.neostore.HomeActivity
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.add_to_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddToCart:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_to_cart)
        getWindow().setExitTransition(null)
        getWindow().setEnterTransition(null)
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
        mActionBarToolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed() })
val totalamount:TextView=findViewById(R.id.totalamount)
        placeorder.setOnClickListener {
            val intent:Intent=Intent(applicationContext, AddressActivity::class.java)
            startActivity(intent)
        }
        val token: String =
            SharedPrefManager.getInstance(
                applicationContext
            ).user.access_token.toString()
        RetrofitClient.instancecart.listcart(token).enqueue(object :
            Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {

                val res = response
                if (response.isSuccessful) {

                    val retro: List<DataCart> = response.body()!!.data
                    totalamount.setText(response.body()?.total.toString())

                    generateDataList(retro?.toMutableList())

                }
            }

        })
    }
    fun generateDataList(dataList: MutableList<DataCart?>?) {
        val recyclerView=findViewById<RecyclerView>(R.id.addtocartrecyleview) as? RecyclerView
        val linear:LinearLayoutManager=
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager=linear
        val adapter = CartAdapter(this@AddToCart, dataList)
        recyclerView?.adapter=adapter
        recyclerView?.addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.divider)))
        recyclerView?.setHasFixedSize(true)

        adapter.notifyDataSetChanged()
//        this@AddToCart.recreate()

        if (dataList?.isEmpty() ?: true) {
            recyclerView?.setVisibility(View.GONE)
            totalamount.setVisibility(View.GONE)
            fl_footer.setVisibility(View.GONE)
            placeorder.setVisibility(View.GONE)
            emptytext.setVisibility(View.VISIBLE)
        } else {
            recyclerView?.setVisibility(View.VISIBLE)
            totalamount.setVisibility(View.VISIBLE)
            fl_footer.setVisibility(View.VISIBLE)
            placeorder.setVisibility(View.VISIBLE)
            emptytext.setVisibility(View.GONE)


        }
      recyclerView?.addOnScrollListener(object :
          RecyclerView.OnScrollListener() {
          override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
              super.onScrollStateChanged(recyclerView, newState)
              Log.e("RecyclerView", "onScrollStateChanged")
          }

          override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
              super.onScrolled(recyclerView, dx, dy)
          }
      })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
startActivity(intent)
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

    override fun onResume() {
        super.onResume()

    }
}