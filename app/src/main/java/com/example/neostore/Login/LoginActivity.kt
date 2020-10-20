package com.example.neostore.Login

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neostore.ForgotPassword
import com.example.neostore.HomeActivity
import com.example.neostore.R
import com.example.neostore.Login.RegisterActivity
import com.zhuinden.liveeventsample.utils.observe
import kotlinx.android.synthetic.main.login_activity.*


/*
class LoginActivity : BaseClassActivity() {

    private lateinit var viewModel: ViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val dataSource = DataSource(applicationContext)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(dataSource)).get(ViewModels::class.java)

        val button = findViewById<ImageView>(R.id.plusbutton)
        val forgotpassword = findViewById<TextView>(R.id.forgotpassword)

        viewModel.loginSuccess.observe(this, Observer {
            if(it) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        })

        viewModel.loginFailedMessage.observe(this, Observer {
            showToast(applicationContext, it)
        })

        button.setOnClickListener {
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }
        forgotpassword.setOnClickListener {
            val i = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(i)
        }

        loginbtn.setOnClickListener {
            val email = loginuser.text.toString().trim()
            val password = loginpassword.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing", Toast.LENGTH_LONG
                ).show()
                loginuser.error = "Email required"
                loginuser.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                loginpassword.error = "Password required"
                loginpassword.requestFocus()
                return@setOnClickListener
            } else {
                viewModel.login(email, password)
            }
        }
    }
}

*/
class LoginActivity : AppCompatActivity() {
    lateinit var model: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val button = findViewById<ImageView>(R.id.plusbutton)
        val forgotpassword=findViewById<TextView>(R.id.forgotpassword)
        button.setOnClickListener {
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }
        forgotpassword.setOnClickListener{
            val i = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(i)
        }
        model = ViewModelProvider(this)[UserViewModel::class.java]
        model.LoginResponseData.observe(this, object : Observer<LoginResponse?> {
            override fun onChanged(t: LoginResponse?) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                startActivity(intent)
                finish()
            }

        })


     /*   loginuser.doOnTextChanged { text, start, before, count ->
            model.user.value= text?.toString()
        }

        loginpassword.doOnTextChanged { text, start, before, count ->
            model.password.value= text?.toString()
        }

        model.loginResult.observe(this) { result ->
            when (result) {
                UserViewModel.LoginResult.UserMissing -> {
                    Toast.makeText(
                        applicationContext, "Data is missing", Toast.LENGTH_LONG
                    ).show()
                    loginuser.error = "Email required"
                    loginuser.requestFocus()
                }
                UserViewModel.LoginResult.PasswordMissing -> {
                    loginpassword.error = "Password required"
                    loginpassword.requestFocus()
                }
                else -> {

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                startActivity(intent)
                        finish ()
            }
            }.safe()
        }

      */
        loginbtn.setOnClickListener {
            val email = loginuser.text.toString().trim()
            val password = loginpassword.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(
                    applicationContext, "Data is missing", Toast.LENGTH_LONG
                ).show()
                loginuser.error = "Email required"
                loginuser.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                loginpassword.error = "Password required"
                loginpassword.requestFocus()
                return@setOnClickListener
            }
            else {
           model.loadLogin(email, password)


            }
        }
    }}




/*class LoginActivity : BaseClassActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val button = findViewById<ImageView>(R.id.plusbutton)
        val forgotpassword = findViewById<TextView>(R.id.forgotpassword)
        button.setOnClickListener {
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }
        forgotpassword.setOnClickListener {
            val i = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(i)
        }


        loginbtn.setOnClickListener {

            val model: DataViewModel = ViewModelProvider(this).get(DataViewModel
            ::class.java)
            model?.getUser()?.observe(this, object : Observer<LoginResponse?> {
                override fun onChanged(t: LoginResponse?) {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }
            })
            }
        }
    }

 */


/*class LoginActivity : BaseClassActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val button = findViewById<ImageView>(R.id.plusbutton)
        val forgotpassword = findViewById<TextView>(R.id.forgotpassword)

        button.setOnClickListener {
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }

        forgotpassword.setOnClickListener {
            val i = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(i)
        }

        loginuser.onTextChanged {
            viewModel.user.value = it.toString()
        }

        loginpassword.onTextChanged {
            viewModel.password.value = it
        }

        loginbtn.setOnClickListener {
            viewModel.login()
        }

        viewModel.loginResult.observe(this) { result ->
            when (result) {
                LoginViewModel.LoginResult.UserMissing -> {
                    Toast.makeText(
                        applicationContext, "Data is missing", Toast.LENGTH_LONG
                    ).show()
                    loginuser.error = "Email required"
                    loginuser.requestFocus()
                }
                LoginViewModel.LoginResult.PasswordMissing -> {
                    loginpassword.error = "Password required"
                    loginpassword.requestFocus()
                }
                LoginViewModel.LoginResult.NetworkFailure -> {
                }
                LoginViewModel.LoginResult.NetworkError -> {
                    showToast(applicationContext, result.userMessage)
                }
                LoginViewModel.LoginResult.Success -> {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    //showToast(applicationContext, res.body()?.message)
                   // Log.d("kjsfgxhufb", response.body()?.status.toString())
                    startActivity(intent)
                    finish()
                }
                else -> {

                }
            }.safe()
        }
    }
    fun <T> T.safe(): T = this // helper method

}


 */


