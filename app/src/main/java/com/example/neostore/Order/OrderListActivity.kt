package com.example.neostore.Order

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.R
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.BaseClassActivity
import com.example.neostore.SharedPrefManager
import com.example.neostore.table.ProductAdapter
import com.example.neostore.table.ProductViewModel
import com.example.neostore.table.Table_response
import com.example.neostore.table.Tabledata
import kotlinx.android.synthetic.main.table_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListActivity : BaseClassActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.table_activity)

        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
       setScreenTitle("My Orders")

        val model = ViewModelProvider(this)[OrderListViewModel::class.java]

        model.orderList?.observe(this,object : Observer<Order_Response_Base> {
            override fun onChanged(t: Order_Response_Base?) {

   generateDataList(t?.data!!)
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
