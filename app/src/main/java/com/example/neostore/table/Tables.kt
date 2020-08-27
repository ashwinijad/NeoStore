package com.example.neostore.table

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.R
import com.example.neostore.RetrofitClient
import kotlinx.android.synthetic.main.table_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Tables : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: Table_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        //setSupportActionBar(toolbar);
       /* toolbar.setOnClickListener{
            NavUtils.navigateUpFromSameTask(this)

        }*/
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);

            //supportActionBar?.setTitle("Tables")
            getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.Tables) + "</font>")));

        }
        recyclerView = findViewById(R.id.recyleview)
        recyclerAdapter = Table_Adapter(this)
        recyleview.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyleview.adapter = recyclerAdapter
            RetrofitClient.instancetable.getAllPhotos(product_category_id = "1" ,value = 1).enqueue( object : Callback<Table_response>{
                override fun onFailure(call: Call<Table_response>, t: Throwable) {
                    Toast.makeText(applicationContext,"falied",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<Table_response>,
                    response: Response<Table_response>
                ) {

                    if (response?.body() != null) {

                        recyclerAdapter.setMovieListItems(response.body()?.data!!)
                       // val i=Intent(applicationContext,Product_details::class.java)
                      //  applicationContext.startActivity(i)
                     //   generateDataList(hello)

                    }
                }

            })
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