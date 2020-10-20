package com.example.neostore.Cart

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.Order.Order_Response_Base
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.add_to_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel (context: Application): AndroidViewModel(context) {
    private var Cart: MutableLiveData<CartResponse>? = null
    val CartList: MutableLiveData<CartResponse>?
        get() {

            if (Cart == null) {
                Cart = MutableLiveData<CartResponse>()
                loadCartList()
            }
            return Cart
        }
    private fun loadCartList(){
        val token: String =
            SharedPrefManager.getInstance(
                getApplication()
            ).user.access_token.toString()
        RetrofitClient.instance.listcart(token).enqueue(object :
            Callback<CartResponse> {
            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(getApplication(), "falied", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<CartResponse>,
                response: Response<CartResponse>
            ) {

                if (response.isSuccessful) {

                    Cart!!.value = response.body()

                }
            }

        })

    }
}