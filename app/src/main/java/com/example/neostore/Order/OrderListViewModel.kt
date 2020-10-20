package com.example.neostore.Order

import android.app.Application
import android.content.Intent.getIntent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.OrderDetail.Order_Detail_Res
import com.example.neostore.OrderDetail.Order_Detail_Response_Base
import com.example.neostore.SharedPrefManager
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListViewModel(context: Application): AndroidViewModel(context) {



    private var OrderLists: MutableLiveData<Order_Response_Base>? = null
    val orderList: MutableLiveData<Order_Response_Base>?
        get() {
            if (OrderLists == null) {
                OrderLists = MutableLiveData<Order_Response_Base>()
                loadOrderList()
            }
            return OrderLists
        }


    private var OrderDetails: MutableLiveData<Order_Detail_Response_Base>? = null
    val orderdetails: MutableLiveData<Order_Detail_Response_Base>?
        get() {
            if (OrderDetails == null) {
                OrderDetails = MutableLiveData<Order_Detail_Response_Base>()
               // loadOrderDetails()
            }
            return OrderDetails
        }

    val token: String =
        SharedPrefManager.getInstance(
            getApplication()
        ).user.access_token.toString()

    //This method is using Retrofit to get the JSON data from URL
    private fun loadOrderList() {


        RetrofitClient.instance.getAllOrderList(token).enqueue(object :
            Callback<Order_Response_Base> {
            override fun onFailure(call: Call<Order_Response_Base>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Order_Response_Base>,
                response: Response<Order_Response_Base>
            ) {

                if (response.isSuccessful) {

                    OrderLists!!.value = response.body()

                }
            }

        })
    }
 /*   private fun loadOrderDetails(){
        val mIntent = getIntent()
        val stringValue = mIntent.extras!!.getString("id")
        RetrofitClient.instance.fetchorderdetail(token, id).enqueue(object :
            Callback<Order_Detail_Response_Base> {
            override fun onFailure(call: Call<Order_Detail_Response_Base>, t: Throwable) {
                Toast.makeText(getApplication(), "falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Order_Detail_Response_Base>,
                response: Response<Order_Detail_Response_Base>
            ) {

                if (response.isSuccessful) {

                    var res = response
                    Log.e("checkresponsee", response.body().toString())
                    val retro: List<Order_Detail_Res> = response.body()!!.data.order_details
                    //    generateDataList(retro)
                    /////   totalidcost.setText(response.body()!!.data.cost.toString())
                }
            }

        })
    }


  */
}
