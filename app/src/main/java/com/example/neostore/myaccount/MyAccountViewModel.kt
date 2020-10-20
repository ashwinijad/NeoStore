package com.example.neostore.myaccount

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.SharedPrefManager
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MyAccountViewModel(context: Application) : AndroidViewModel(context) {
    private val _accountResponseData = MutableLiveData<Myaccountbaseresponse?>()
    val accountResponseData: MutableLiveData<Myaccountbaseresponse?>
        get() = _accountResponseData

    init {
        loadAccountData()
    }

    fun loadAccountData() {
        val token: String = SharedPrefManager.getInstance(getApplication()).user.access_token.toString()
        RetrofitClient.instance.fetchUser(token)
            .enqueue(object : Callback<Myaccountbaseresponse> {
                override fun onFailure(call: Call<Myaccountbaseresponse>, t: Throwable) {
                    Log.d("res", "" + t)
                    _accountResponseData.value = null
                }

                override fun onResponse(
                    call: Call<Myaccountbaseresponse>,
                    response: Response<Myaccountbaseresponse>
                ) {
                    var res = response

                    if (res.body()?.status == 200) {
                        _accountResponseData.value = response.body()
                    } else {
                        try {
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            Toast.makeText(
                                getApplication(),
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Log.e("errorrr", e.message)
                        }
                    }
                }
            })
    }
    /*
    class MyAccountViewModel(context: Application) :AndroidViewModel(context),LifecycleObserver{
    private var MyAccountViewModels: MutableLiveData<My_account_base_response>? = null
    val viewmodel: MutableLiveData<My_account_base_response>?
        get() {
            if (MyAccountViewModels == null) {
                MyAccountViewModels = MutableLiveData<My_account_base_response>()
                loadviewmodel()
            }else {
                return MyAccountViewModels
            }
            return null
        }


    private fun loadviewmodel(){
        val token :String = SharedPrefManager.getInstance(getApplication()).user.access_token.toString()
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
                        MyAccountViewModels!!.value = response.body()

                    } else {
                        try {
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            Toast.makeText(getApplication(),
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Log.e("errorrr", e.message)
                        }
                    }
                }
            })
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected fun onLifeCycleResume() {
loadviewmodel()    }
}
     */
}

