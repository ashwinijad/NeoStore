package com.example.neostore.table

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.HomeActivity
import com.example.neostore.R


class FavouriteActivity : AppCompatActivity() {
    private var rv: RecyclerView? = null
    private var adapter: FavouriteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        rv = findViewById<View>(R.id.recyleview) as RecyclerView
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)

        favData
    }
    override fun onResume() {
        super.onResume()
        favData
    }
    private val favData: Unit
        private get() {
            val favoriteLists: List<Tabledata>? =
                HomeActivity.favoriteDatabase?.favoriteDao()?.favoriteData
            //        List<RetroPhoto> favouritesList = PubImgApplication.getInstance().getDatabase().getAllFavourites();
          ///  adapter = favoriteLists?.let { FavouriteAdapter(it, applicationContext) }
            adapter = FavouriteAdapter(favoriteLists as MutableList<Tabledata>, applicationContext)

            rv!!.adapter = adapter
        }

}
