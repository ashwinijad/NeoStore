package com.example.neostore.Address

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.neostore.Address.Room.Address
import com.example.neostore.Address.Room.Database
import com.example.neostore.R
import kotlinx.android.synthetic.main.addaddress.*
import kotlinx.coroutines.launch


/*class AddAddress: AppCompatActivity() {
    var favoriteDatabase: Database? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addaddress)
val editText:EditText=findViewById(R.id.longaddress)
     this.favoriteDatabase = Room.databaseBuilder<Database>(
            applicationContext,
            Database::class.java, "myfavdb"
        ).allowMainThreadQueries().build()
        saveaddress.setOnClickListener {
            val value: String = editText.getText().toString()
            val finalValue = value
            val favoriteList = Table()


            val name: String = finalValue

            favoriteList.address=name
            favoriteDatabase!!.AddressDao()?.addData(favoriteList);
val intent=Intent(applicationContext,Address::class.java)
            startActivity(intent)


        }
    }
}*/
class  AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addaddress)

        val editText: EditText = findViewById(R.id.longaddress)

        val application = application as CustomApplication

        saveaddress.setOnClickListener {
            val address = Address()
            address.address = editText.getText().toString()

            lifecycleScope.launch {
                 application.addressDao.addData(address)
                finish()
            }

        }
    }
}