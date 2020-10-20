package com.example.neostore.UI

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.BaseClassActivity
import com.example.neostore.R
import com.example.neostore.ClientApi.RetrofitClient
import com.example.neostore.table.ProductAdapter
import com.example.neostore.table.ProductViewModel
import com.example.neostore.table.Table_response
import com.example.neostore.table.Tabledata
import kotlinx.android.synthetic.main.table_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cupboards:BaseClassActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: ProductAdapter
    var Tablelist : MutableList<Tabledata> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        //setSupportActionBar(toolbar);
        /* toolbar.setOnClickListener{
            NavUtils.navigateUpFromSameTask(this)

        }*/


        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        setScreenTitle("CupBoards")

        recyclerView = findViewById(R.id.recyleview)

        val model =
            ViewModelProvider(this)[ProductViewModel::class.java]

        model.Cupboard?.observe(this, object : Observer<Table_response> {
            override fun onChanged(t: Table_response?) {

                recyclerAdapter = ProductAdapter(applicationContext, Tablelist)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        recyclerView.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                recyclerAdapter.setMovieListItems(t?.data as MutableList<Tabledata>)

                recyclerView.adapter = recyclerAdapter
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