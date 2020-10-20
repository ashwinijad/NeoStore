package com.example.neostore.Login

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.ForgotResponse
import com.example.neostore.HomeActivity
import com.example.neostore.ResetPassword.ErrorUtils
import com.example.neostore.ResetPassword.Reset_Reponse_Base
import com.example.neostore.SharedPrefManager
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(context: Application, private val savedStateHandle: SavedStateHandle) : AndroidViewModel(
    context) {
    /*
    sealed class LoginResult {
        object UserMissing : LoginResult()

        object PasswordMissing : LoginResult()
    }

     */
    private var _loginResponseData = MutableLiveData<LoginResponse?>()
    private  var _regResponseData=MutableLiveData<LoginResponse?>()
    private  var _forgotPassword=MutableLiveData<ForgotResponse?>()
    private  var _resetPassword=MutableLiveData<Reset_Reponse_Base?>()


    val firstname: MutableLiveData<String> = savedStateHandle.getLiveData("firstname", "")
    val lastname: MutableLiveData<String> = savedStateHandle.getLiveData("lastname", "")
    val user: MutableLiveData<String> = savedStateHandle.getLiveData("user", "")
    val password: MutableLiveData<String> = savedStateHandle.getLiveData("password", "")
    val email: MutableLiveData<String> = savedStateHandle.getLiveData("email", "")
    val gender:String?=null
    val phone_no: MutableLiveData<String> = savedStateHandle.getLiveData("phone_no", "")
    val old:String?=null
    val newpwd :String?=null
    val confpwd:String?=null

   // private val loginResultEmitter = EventEmitter<LoginResult>()
   // val loginResult: EventSource<LoginResult> = loginResultEmitter
    val LoginResponseData: LiveData<LoginResponse?>
        get() {

            if (_loginResponseData == null) {
                _loginResponseData = MutableLiveData<LoginResponse?>()
                loadLogin(user.toString(), password.toString())
            }
            return _loginResponseData
        }

    val RegisterResponseData: LiveData<LoginResponse?>
        get() {

            if (_regResponseData == null) {
                _regResponseData = MutableLiveData<LoginResponse?>()
                loadreg(firstname.toString(),lastname.toString(),user.toString(), password.toString(),confpwd.toString(),gender.toString(),phone_no.toString())
            }
            return _regResponseData
        }

    val ForgotPasswordData:LiveData<ForgotResponse?>
    get() {
        if (_forgotPassword == null) {
            _forgotPassword = MutableLiveData<ForgotResponse?>()
            loadForgot(email.toString())
        }
        return _forgotPassword
    }

    val Resetpwd:LiveData<Reset_Reponse_Base?>
        get() {
            if (_resetPassword == null) {
                _resetPassword = MutableLiveData<Reset_Reponse_Base?>()
                resetpwd(old.toString(),newpwd.toString(),confpwd.toString())
            }
            return _resetPassword
        }


    fun loadLogin(email: String, password: String) {
/*
        if (email.isEmpty()) {
            loginResultEmitter.emit(LoginResult.UserMissing)
            return
        }


        if (password.isEmpty()) {
            loginResultEmitter.emit(LoginResult.PasswordMissing)
            return
        }

 */
        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("res", "" + t)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    var res = response

                    if (res.body()?.status == 200) {
                        SharedPrefManager.getInstance(getApplication())
                            .saveUser(response.body()?.data!!)


                        //finally we are setting the list to our MutableLiveData
                        _loginResponseData.postValue(response.body())
                     //   loginResultEmitter.emit(LoginResult.Success)



                        Toast.makeText(
                            getApplication(), res.body()?.user_msg,
                            Toast.LENGTH_LONG
                        ).show()
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
    fun loadreg(firstname:String,lastname:String,user:String, password:String,confpwd:String,gender:String,phone_no:String){
        RetrofitClient.instance.createUser(firstname, lastname, user, password, confpwd,gender ,phone_no)
            .enqueue(object: Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(getApplication(), t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    var res = response
                    if (response.isSuccessful() && response.body() != null) {
                        SharedPrefManager.getInstance(getApplication())
                            .saveUser(response.body()?.data!!)


                        Toast.makeText(
                            getApplication(),
                            res.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()

                        _regResponseData.postValue(response.body())

                    }
                    else
                    {
                        try {
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            Toast.makeText(
                                getApplication(),
                                jObjError.getString("message"),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(getApplication(), e.message, Toast.LENGTH_LONG).show()
                            Log.e("errorrr",e.message)
                        }
                    }
                }
            })
    }
    fun loadForgot(email: String)
    {
            RetrofitClient.instance.emailForgotpwd(email)
                .enqueue(object : Callback<ForgotResponse> {
                    override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {

                        Log.d("res", "" + t)
                        _forgotPassword.value = null

                    }

                    override fun onResponse(
                        call: Call<ForgotResponse>,
                        response: Response<ForgotResponse>
                    ) {
                        var res = response

                        Log.d("response check ", "" + response.body()?.status.toString())
                        if (res.body()?.status==200) {
                            Toast.makeText(getApplication(),res.body()?.user_msg,Toast.LENGTH_LONG).show()
                            // forgotCallBack.onSuccess()
                            _forgotPassword.postValue(response.body())

                        }
                        else{
                            try {
                                val jObjError =
                                    JSONObject(response.errorBody()!!.string())
                                Toast.makeText(getApplication(),jObjError.getString("user_msg"),Toast.LENGTH_LONG).show()

                            } catch (e: Exception) {
                                Toast.makeText(getApplication(),e.message,Toast.LENGTH_LONG).show()


                            }
                        }
                    }
                })

        }
    fun resetpwd(old:String,newpwd:String,confpwd:String){
        val token: String =
            SharedPrefManager.getInstance(
                getApplication()
            ).user.access_token.toString()
        RetrofitClient.instance.resetpassword(token, old, newpwd, confpwd)
            .enqueue(object : Callback<Reset_Reponse_Base> {
                override fun onFailure(call: Call<Reset_Reponse_Base>, t: Throwable) {

                    Log.d("res", "" + t)


                }

                override fun onResponse(
                    call: Call<Reset_Reponse_Base>,
                    response: Response<Reset_Reponse_Base>
                ) {
                    var res = response

                    if (res.body()?.status == 200) {

                        Log.d("response check ", "" + response.body()?.status.toString())
                        Toast.makeText(getApplication(),res.body()?.user_msg,Toast.LENGTH_LONG).show()
                        _resetPassword.postValue(response.body())


                    }
                    else{
                        val error: Reset_Reponse_Base = ErrorUtils.parseError(response)

                        if(newpwd.length <= 5 && confpwd.length<=5)
                        {
                            Toast.makeText(getApplication(),"password should more than 6 letters or digits",Toast.LENGTH_LONG).show()

                        }
                        else{

                            Toast.makeText(getApplication(),error.error.confirm_password.compareWith,Toast.LENGTH_LONG).show()
                        }
                    }

                }



            })
    }


}
/*
class LoginViewModel(  application: Application,private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {
    sealed class LoginResult {
        object UserMissing : LoginResult()

        object PasswordMissing : LoginResult()

        class NetworkError(val userMessage: String) : LoginResult()

        object NetworkFailure : LoginResult()

        object Success : LoginResult()
    }
    //private val applicationContext = application

    val user: MutableLiveData<String> = savedStateHandle.getLiveData("user", "")
    val password: MutableLiveData<String> = savedStateHandle.getLiveData("password", "")

    private val loginResultEmitter = EventEmitter<LoginResult>()
    val loginResult: EventSource<LoginResult> = loginResultEmitter

    fun login() {
        val email = user.value!!.toString().trim()
        val password = password.value!!.toString().trim()

        if (email.isEmpty()) {
            loginResultEmitter.emit(LoginResult.UserMissing)
            return
        }


        if (password.isEmpty()) {
            loginResultEmitter.emit(LoginResult.PasswordMissing)
            return
        }

        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("res", "" + t)
                    loginResultEmitter.emit(LoginResult.NetworkFailure)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    var res = response

                    Log.d("response check ", "" + response.body()?.status.toString())
                    if (res.body()?.status == 200) {
                        SharedPrefManager.getInstance(getApplication()).saveUser(response.body()?.data!!)
                        loginResultEmitter.emit(LoginResult.Success)
                    } else {
                        try {
                            val jObjError =
                                JSONObject(response.errorBody()!!.string())
                            loginResultEmitter.emit(LoginResult.NetworkError(jObjError.getString("user_msg")))
                        } catch (e: Exception) {
                            // showToast(applicationContext,e.message) // TODO
                            Log.e("errorrr", e.message)
                        }
                    }
                }
            })
    }
}

 */