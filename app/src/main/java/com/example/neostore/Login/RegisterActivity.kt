package com.example.neostore.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.HomeActivity
import com.example.neostore.R
import com.example.neostore.SharedPrefManager
import kotlinx.android.synthetic.main.register_activity.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity(){
    lateinit var model: UserViewModel

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.register_activity)
        val toolbar: ImageView = findViewById(R.id.toolbar)
        //toolbar.setNavigationIcon(resources.getDrawable(R.drawable.ic_arrow_back_black_24dp)) // your drawable

        //setSupportActionBar(toolbar);
        toolbar.setOnClickListener{
            NavUtils.navigateUpFromSameTask(this)

        }
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }
        //https://www.learn2crack.com/2013/08/develop-android-login-registration-with-php-mysql.html/2
        model = ViewModelProvider(this)[UserViewModel::class.java]
        model.RegisterResponseData.observe(this, object : Observer<LoginResponse?> {
            override fun onChanged(t: LoginResponse?) {
                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)

                startActivity(intent)
                finish()
            }

        })
        registerbtn.setOnClickListener {

            val first_name = registerfirstname.text.toString().trim()
            val last_name = registerlastname.text.toString().trim()
            val email = emailregister.text.toString().trim()
            val password = regpass.text.toString().trim()
            val confirm_password = confregpass.text.toString().trim()
            var checkedId=1
            //  var gender = (findViewById<View>(R.id.radioGrp) as RadioGroup).clearCheck().toString()
            val selectedId: Int = radioGrp.checkedRadioButtonId
            var gender = if (selectedId != -1) {
                val selectedRadioButton =
                    findViewById<RadioButton>(selectedId)
                selectedRadioButton.text.toString()
            } else {
                ""
            }
            val phone_no = phonereg.text.toString().trim()


            if(first_name.isEmpty()){
                registerfirstname.error = "Email required"
                registerfirstname.requestFocus()
                return@setOnClickListener
            }



            if(last_name.isEmpty()){
                registerlastname.error = "Password required"
                registerlastname.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                emailregister.error = "Name required"
                emailregister.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                regpass.error = "School required"
                regpass.requestFocus()
                return@setOnClickListener
            }

            if(confirm_password.isEmpty()){
                confregpass.error = "School required"
                confregpass.requestFocus()
                return@setOnClickListener
            }

            if(phone_no.isEmpty()){
                phonereg.error = "School required"
                confregpass.requestFocus()
                return@setOnClickListener
            }

            model.loadreg(first_name,last_name,email,password,confirm_password,gender,phone_no)
        }
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 2)
        super.onBackPressed()
    }
}