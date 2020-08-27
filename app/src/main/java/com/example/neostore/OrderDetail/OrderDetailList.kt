package com.example.neostore.OrderDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.Cart.DividerItemDecorator
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailList:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        val intent1: Intent =getIntent()

        val id:String =intent1.getStringExtra("id")

        Log.e("getid",id)
        val token: String =
            SharedPrefManager.getInstance(
                applicationContext
            ).user.access_token.toString()
        RetrofitClient.instanceorder.fetchorderdetail(token,id).enqueue( object :
            Callback<Order_Detail_Response_Base> {
            override fun onFailure(call: Call<Order_Detail_Response_Base>, t: Throwable) {
                Toast.makeText(applicationContext,"falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Order_Detail_Response_Base>,
                response: Response<Order_Detail_Response_Base>
            ) {

                if (response.isSuccessful ){

                    var res = response
                    Log.e("checkresponsee",response.body().toString())
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
        val adapter = Order_Detail_Adapter(this@OrderDetailList,dataList)
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.divider)))
        recyclerView.setHasFixedSize(true)

        adapter.notifyDataSetChanged()

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
        // val intent = Intent(this, HomeActivity::class.java)
        //startActivityForResult(intent, 2)
        super.onBackPressed()
    }
}



