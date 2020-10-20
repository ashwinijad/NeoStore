package com.example.neostore.OrderDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.BaseClassActivity
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.Order.OrderListActivity
import com.example.neostore.R
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.order_detail_item_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailList:BaseClassActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setExitTransition(null)
        getWindow().setEnterTransition(null)
        setContentView(R.layout.order_detail_item_activity)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        }
        val intent1: Intent = getIntent()

        val id:String =intent1.getStringExtra("id")
        supportActionBar!!.title = id
        getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\"" + "<b>" + "ORDER ID:" + id + "</font>")));



        //  supportActionBar!!.title = id
        Log.e("getid", id)
        val token: String =
            SharedPrefManager.getInstance(
                applicationContext
            ).user.access_token.toString()
        RetrofitClient.instance.fetchorderdetail(token, id).enqueue(object :
            Callback<Order_Detail_Response_Base> {
            override fun onFailure(call: Call<Order_Detail_Response_Base>, t: Throwable) {
                Toast.makeText(applicationContext, "falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Order_Detail_Response_Base>,
                response: Response<Order_Detail_Response_Base>
            ) {

                if (response.isSuccessful) {

                    var res = response
                    Log.e("checkresponsee", response.body().toString())
                    val retro: List<Order_Detail_Res> = response.body()!!.data.order_details
                    generateDataList(retro)
                }
            }

        })
    }
    fun generateDataList(dataList: List<Order_Detail_Res>) {
        val recyclerView=findViewById<RecyclerView>(R.id.recyleview)
        val linear: LinearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager=linear
        val adapter = Order_Detail_Adapter(this@OrderDetailList, dataList)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setHasFixedSize(true)

        adapter.notifyDataSetChanged()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {

            finish() // close this activity and return to preview activity (if there is any)
            overridePendingTransition(0, 0)

        }
        return super.onOptionsItemSelected(item)
    }

}



