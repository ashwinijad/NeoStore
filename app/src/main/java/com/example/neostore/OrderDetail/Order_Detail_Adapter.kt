package com.example.neostore.OrderDetail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.Order.OrderListActivity
import com.example.neostore.Order.Order_Response_Data
import com.example.neostore.R

class Order_Detail_Adapter(var context: Context, var Tablelist: List<Order_Detail_Res>) :
    RecyclerView.Adapter<Order_Detail_Adapter.MyViewHolder>() {

    init {
        this.context = context
        this.Tablelist = Tablelist
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_detail_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Tablelist.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.productname.text = Tablelist.get(position).prod_name.toString()
        Glide.with(context).load(Tablelist.get(position).prod_image)
            .into(holder.productimage)
        holder.productcategory.text="("+Tablelist.get(position).prod_cat_name+")"
        holder.productcost.text=Tablelist.get(position).total.toString()
        holder.quantity.text=Tablelist.get(position).quantity.toString()
        Log.e("checkkkkk",Tablelist.get(position).id.toString())


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productname: TextView
       val productimage: ImageView
        val productcategory:TextView
        val productcost: TextView
val quantity:TextView
        init {

            productname= itemView.findViewById(R.id.name)
            productimage=itemView.findViewById(R.id.productimg)
            productcategory=itemView.findViewById(R.id.productcategory)
            productcost=itemView.findViewById(R.id.cost)
            quantity=itemView.findViewById(R.id.quantityno)
        }

    }
}