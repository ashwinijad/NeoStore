package com.example.neostore.Order

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
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);

            //supportActionBar?.setTitle("Tables")
            getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.MyOrder) + "</font>")));


        }


        val token: String =
            SharedPrefManager.getInstance(
                applicationContext
            ).user.access_token.toString()
        RetrofitClient.instanceorder.getAllOrderList(token).enqueue(object :
            Callback<Order_Response_Base> {
            override fun onFailure(call: Call<Order_Response_Base>, t: Throwable) {
                Toast.makeText(applicationContext, "falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Order_Response_Base>,
                response: Response<Order_Response_Base>
            ) {

                if (response.isSuccessful) {

                    var res = response
                    Log.e("checkresponsee", response.body().toString())
                    val retro: List<Order_Response_Data> = response.body()!!.data
                    generateDataList(retro)
                }
            }

        })
    }
    fun generateDataList(dataList: List<Order_Response_Data>) {
        val recyclerView=findViewById<RecyclerView>(R.id.recyleview)
        val linear:LinearLayoutManager=
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager=linear
        val adapter = Order_Adapter(this@OrderListActivity, dataList)
        recyclerView.adapter=adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

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
