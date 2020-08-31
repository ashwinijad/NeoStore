package com.example.neostore.table

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.ProductDetails.Product_details
import com.example.neostore.R

class Table_Adapter(val context: Context) : RecyclerView.Adapter<Table_Adapter.MyViewHolder>(),Filterable {

    var tablelist: List<Tabledata> = listOf()
    var filtered: MutableList<Tabledata> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.table_recycle_item,
            parent,
            false
        )
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return tablelist.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tableItem = tablelist.get(position)

        holder.productname.text = tableItem.name
        holder.producername.text = tableItem.producer

        holder.productprice.text = tableItem.cost.toString()

        Glide.with(holder.itemView!!.context).load(tableItem.product_images)
            .into(holder.image)

        holder.rate.setRating(tableItem.rating.toFloat());
        holder.itemView!!.setOnClickListener {
            val context: Context = holder.itemView.context
            val i = Intent(
                context,
                Product_details::class.java
            )
            i.putExtra("id", tableItem.id.toString())
            i.putExtra("image", tableItem.product_images)
            context.startActivity(i)
        }
    }

    fun setMovieListItems(movieList: MutableList<Tabledata>) {
        filtered = movieList.toMutableList() // makes a copy
        tablelist = movieList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val productname: TextView = itemView!!.findViewById(R.id.title)
        val producername: TextView = itemView!!.findViewById(R.id.title1)
        val productprice: TextView = itemView!!.findViewById(R.id.title2)
        val rate: RatingBar=itemView!!.findViewById(R.id.ratingbar)
        val image: ImageView = itemView!!.findViewById(R.id.image)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val queryString = charSequence.toString()

                val filterResults = FilterResults()
                filterResults.values =
                    if (queryString.isEmpty()) {
                        filtered
                    } else {
                        filtered.filter {
                            it.name.contains(queryString, ignoreCase = true) || it.name.contains(charSequence)
                        }
                    }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                tablelist = filterResults.values as List<Tabledata>
                notifyDataSetChanged()
            }
        }
    }
}