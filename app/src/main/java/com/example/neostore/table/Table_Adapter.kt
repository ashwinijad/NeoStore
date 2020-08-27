package com.example.neostore.table

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.ProductDetails.Product_details
import com.example.neostore.R

class Table_Adapter(val context: Context) : RecyclerView.Adapter<Table_Adapter.MyViewHolder>() {

    var Tablelist : List<Table_data> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.table_recycle_item,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Tablelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.productname.text = Tablelist.get(position).name
        holder.producername.text = Tablelist.get(position).producer

        holder.productprice.text = Tablelist.get(position).cost.toString()

        Glide.with(context).load(Tablelist.get(position).product_images)
            .into(holder.image)
        holder.rate.setRating(Tablelist.get(position).rating.toFloat());
        Log.e("checkratevalue", Tablelist.get(position).rating.toFloat().toString())

        val hi=Tablelist.get(1).rating.toFloat()
        Log.e("checkratevalue", hi.toFloat().toString())

        //   holder.rate.setStepSize(Tablelist.get(position).rating);// to show to stars


        holder.itemView!!.setOnClickListener {


            val context:Context=holder.itemView.context
            val i=Intent(
                context,
                Product_details::class.java
            )
            i.putExtra("id", Tablelist.get(position).id.toString())
            i.putExtra("image", Tablelist.get(position).product_images)

            Log.e("checkid", Tablelist.get(position).id.toString())
            context.startActivity(i)
        }
    }

    fun setMovieListItems(movieList: List<Table_data>){
        this.Tablelist = movieList;
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val productname: TextView = itemView!!.findViewById(R.id.title)
        val producername: TextView = itemView!!.findViewById(R.id.title1)
        val productprice: TextView = itemView!!.findViewById(R.id.title2)
        val rate: RatingBar=itemView!!.findViewById(R.id.ratingbar)

        val image: ImageView = itemView!!.findViewById(R.id.image)

    }
}