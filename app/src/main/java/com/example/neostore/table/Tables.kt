package com.example.neostore.table

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.core.view.MenuItemCompat
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
     var Tablelist : MutableList<Tabledata> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
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
            RetrofitClient.instancetable.getAllPhotos(product_category_id = "1", value = 1).enqueue(
                object : Callback<Table_response> {
                    override fun onFailure(call: Call<Table_response>, t: Throwable) {
                        Toast.makeText(applicationContext, "falied", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<Table_response>,
                        response: Response<Table_response>
                    ) {

                        if (response.body() != null) {

                            recyclerAdapter.setMovieListItems((response.body()?.data as MutableList<Tabledata>?)!!)
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search: MenuItem = menu.findItem(R.id.search)
        val searchView: SearchView = MenuItemCompat.getActionView(search) as SearchView
        val searchEditText =
            searchView.findViewById<View>(R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.white))
        searchEditText.setHintTextColor(resources.getColor(R.color.white))
      //  searchEditText.setPadding(0,0,0,-40)
       // searchEditText.setBackgroundTintList(ContextCompat.getColorStateList(this@Tables, R.color.white));
        val searchplate =
            searchView.findViewById(R.id.search_plate) as View
        searchplate.background.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        val searchClose: ImageView =
            searchView.findViewById(R.id.search_close_btn)
        searchClose.setImageResource(R.drawable.remove_button)
      //  searchClose.setPadding(0,0,0,-40)
        search(searchView)
        return true
    }
  override fun onBackPressed() {
      super.onBackPressed()
  }
    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                recyclerAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
}