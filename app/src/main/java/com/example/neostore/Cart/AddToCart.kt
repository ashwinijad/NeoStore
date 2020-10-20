package com.example.neostore.Cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.Address.AddressActivity
import com.example.neostore.BaseClassActivity
import com.example.neostore.HomeActivity
import com.example.neostore.R
import kotlinx.android.synthetic.main.add_to_cart.*


class AddToCart:BaseClassActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_to_cart)
        getWindow().setExitTransition(null)
        getWindow().setEnterTransition(null)
        var mActionBarToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbartable);
        setSupportActionBar(mActionBarToolbar);
        // add back arrow to toolbar
      setEnabledTitle()

        mActionBarToolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed() })
        placeorder.setOnClickListener {
            val intent:Intent=Intent(applicationContext, AddressActivity::class.java)
            startActivity(intent)
        }
       loadCart()
    }

  fun loadCart(){

      val model = ViewModelProvider(this)[CartViewModel::class.java]

      model.CartList?.observe(this,object : Observer<CartResponse> {
          override fun onChanged(t: CartResponse?) {

              generateDataList(t?.data?.toMutableList())
totalamount.setText(t?.total.toString())
          }
  })
  }
    fun generateDataList(dataList: MutableList<DataCart?>?) {
        val recyclerView=findViewById<RecyclerView>(R.id.addtocartrecyleview) as? RecyclerView
        val linear:LinearLayoutManager=
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager=linear
        val adapter = CartAdapter(this@AddToCart, dataList)
        recyclerView?.adapter=adapter
        recyclerView?.addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.divider)))
       // recyclerView?.setHasFixedSize(true)

        adapter.notifyDataSetChanged()
//        this@AddToCart.recreate()

        if (dataList?.isEmpty() ?: true) {
            recyclerView?.setVisibility(View.GONE)
            totalamount.setVisibility(View.GONE)
            fl_footer.setVisibility(View.GONE)
            placeorder.setVisibility(View.GONE)
            emptytext.setVisibility(View.VISIBLE)
        } else {
            recyclerView?.setVisibility(View.VISIBLE)
            totalamount.setVisibility(View.VISIBLE)
            fl_footer.setVisibility(View.VISIBLE)
            placeorder.setVisibility(View.VISIBLE)
            emptytext.setVisibility(View.GONE)


        }
      recyclerView?.addOnScrollListener(object :
          RecyclerView.OnScrollListener() {
          override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
              super.onScrollStateChanged(recyclerView, newState)
              Log.e("RecyclerView", "onScrollStateChanged")
          }

          override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
              super.onScrolled(recyclerView, dx, dy)
          }
      })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
startActivity(intent)
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

    override fun onResume() {
        super.onResume()
loadCart()
    }
}