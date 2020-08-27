package com.example.neostore.Order

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore.OrderDetail.OrderDetailList
import com.example.neostore.R

class Order_Adapter(var context: Context, var Tablelist: List<Order_Response_Data>) : RecyclerView.Adapter<Order_Adapter.MyViewHolder>() {

   init {
       this.context = context
       this.Tablelist = Tablelist
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Tablelist.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.orderid.text = Tablelist.get(position).id.toString()
        holder.orderdate.text=Tablelist.get(position).created
        holder.ordercost.text=Tablelist.get(position).cost.toString()
        Log.e("checkkkkk",Tablelist.get(position).id.toString())

        holder.itemView!!.setOnClickListener {


            val context:Context=holder.itemView.context
            val i=Intent(context,
                OrderDetailList::class.java)
            i.putExtra("id",Tablelist.get(position).id.toString())
            Log.e("checkid",Tablelist.get(position).id.toString())
            context.startActivity(i)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderid: TextView
        val orderdate:TextView
        val ordercost:TextView

        init {

            orderid= itemView.findViewById(R.id.ordertitle)
            orderdate=itemView.findViewById(R.id.orderdate)
            ordercost=itemView.findViewById(R.id.ordercost)
        }

    }
}