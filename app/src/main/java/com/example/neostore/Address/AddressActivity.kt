package com.example.neostore.Address

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.Address.Room.Address
import com.example.neostore.Cart.DividerItemDecorator
import com.example.neostore.ForgotResponse
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.address.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*class Address:AppCompatActivity() {
    private val rv: RecyclerView? = null
     var adapter: AddressAdapter? = null
    var favoriteDatabase: Database? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address)
        addbutton.setOnClickListener {
            val intent = Intent(applicationContext, AddAddress::class.java)
            startActivity(intent)

        }
      val  rv=findViewById<RecyclerView>(R.id.recyclerview)
        val linear: LinearLayoutManager =
            LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager=linear


        val favoriteLists: List<Table>? =
            favoriteDatabase?.AddressDao()?.favoriteData as List<Table>?

        adapter = favoriteLists?.let { AddressAdapter(it, applicationContext) }
        rv.adapter = adapter
        adapter?.notifyDataSetChanged()
        rv.setHasFixedSize(true);

    }

override fun onResume()
{

    super.onResume()
    val  rv=findViewById<RecyclerView>(R.id.recyclerview)
    val linear: LinearLayoutManager =
        LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
    rv.layoutManager=linear


    val favoriteLists: List<Table>? =
        favoriteDatabase?.AddressDao()?.favoriteData as List<Table>?

    adapter = favoriteLists?.let { AddressAdapter(it, applicationContext) }
    rv.adapter = adapter
    adapter?.notifyDataSetChanged()
    rv.setHasFixedSize(true);

}
}*/
class AddressActivity : AppCompatActivity(){
    private lateinit var data: LiveData<MutableList<Address>>
    private  var data1:Int = 0
    var adapter: AddressAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address)

        addbutton.findViewById<View>(R.id.addbutton).setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            startActivity(intent)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
            IntentFilter("custom-message1")
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = AddressAdapter(applicationContext)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("RecyclerView", "onScrollStateChanged")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        val application = application as CustomApplication
        data = application.database.AddressDao().getAddressesWithChanges()
        data.observe(this, Observer { words1 ->
            // Update the cached copy of the words in the adapter.
            words1?.let {  adapter.updateData(it) }})

    }
    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val token: String =
                SharedPrefManager.getInstance(
                    applicationContext
                ).user.access_token.toString()
            val ItemName = intent.getStringExtra("item1")
          ordernow.setOnClickListener {

              RetrofitClient.instanceorder.ordernow(token, ItemName)
                  .enqueue(object : Callback<ForgotResponse> {
                      override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {

                          Log.d("res", "" + t)
                      }
                      override fun onResponse(
                          call: Call<ForgotResponse>,
                          response: Response<ForgotResponse>
                      ) {
                          var res = response

                          if (res.body()?.status == 200) {

                              Log.d("response check ", "" + response.body()?.status.toString())
                              Toast.makeText(
                                  applicationContext,
                                  res.body()?.user_msg,
                                  Toast.LENGTH_LONG
                              ).show()
                              Log.d("kjsfgxhufb", response.body()?.user_msg.toString())
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
                  })}
        }
    }
  /*  override fun onClick(id: Int) {
        val application = application as CustomApplication
        data1 = application.database.AddressDao().deleteAddress(id)
      //  adapter?.updateData1(it,this)
    }*/
}