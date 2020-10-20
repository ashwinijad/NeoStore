package com.example.neostore.ProductDetails

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.neostore.ProductDetails.RoomProdDB.FavProdDB
import com.example.neostore.R
import com.example.neostore.table.Tabledata

class Favouriteprod:AppCompatActivity() {
    private var rv: RecyclerView? = null
    private var adapter: FavouriteprodAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
            getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);

            getSupportActionBar()?.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.MyFavourites) + "</font>")));
        }
        rv = findViewById<View>(R.id.recyleview) as RecyclerView
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)
        Productdetails.favoriteDatabase = Room.databaseBuilder(applicationContext,
            FavProdDB::class.java, "myfavdb").allowMainThreadQueries().build()
        favData
    }
    override fun onResume() {
        super.onResume()
        favData
    }

    private val favData: Unit
        private get() {
            val favoriteLists: List<Tabledata>? =
                Productdetails.favoriteDatabase?.favoriteDao()?.favoriteData
            //        List<RetroPhoto> favouritesList = PubImgApplication.getInstance().getDatabase().getAllFavourites();
            ///  adapter = favoriteLists?.let { FavouriteAdapter(it, applicationContext) }
            adapter = FavouriteprodAdapter(favoriteLists as MutableList<Tabledata>, applicationContext)

            rv!!.adapter = adapter
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