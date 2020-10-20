package com.example.neostore.table

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore.HomeActivity
import com.example.neostore.ProductDetails.Productdetails
import com.example.neostore.R

class ProductAdapter(val context: Context, var tablelist: List<Tabledata>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>(),Filterable {

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
        if (HomeActivity.favoriteDatabase?.favoriteDao()
                ?.isFavorite(tablelist.get(position).id) === 1
        )
            //holder.fav_btn.setImageResource(R.drawable.favourite_red)
        else
           // holder.fav_btn.setImageResource(R.drawable.favourite_null)


      /*  holder.fav_btn.setOnClickListener(View.OnClickListener {

         /*   val favoriteList = Tabledata(tablelist.get(position).id,
                tablelist.get(position).product_category_id,tablelist.get(position).name,
                tablelist.get(position).producer,
                tablelist.get(position).description,
                tablelist.get(position).cost,tablelist.get(position).rating,tablelist.get(position).view_count,
                tablelist.get(position).created,tablelist.get(position).modified,tablelist.get(position).product_images
            )

            val id: Int = tablelist.get(position).id
            val image: String =  tablelist.get(position).product_images
            val name: String =  tablelist.get(position).name

            favoriteList.id=id
            favoriteList.product_images=image
            favoriteList.name=name

          */
            if (HomeActivity.favoriteDatabase?.favoriteDao()
                    ?.isFavorite(tablelist.get(position).id) !== 1
            ) {
              //  holder.fav_btn.setImageResource(R.drawable.favourite_red)
                HomeActivity.favoriteDatabase?.favoriteDao()?.addData(tablelist.get(position))

            } else {
              //  holder.fav_btn.setImageResource(R.drawable.favourite_null)
                HomeActivity.favoriteDatabase!!.favoriteDao().delete(tablelist.get(position))
            }
        })


       */
        holder.itemView!!.setOnClickListener {
            val context: Context = holder.itemView.context
            val i = Intent(
                context,
                Productdetails::class.java
            )
            i.putExtra("id", tableItem.id)
            i.putExtra("product_category_id", tableItem.product_category_id)
            i.putExtra("name", tablelist.get(position).name)
            i.putExtra("producer", tablelist.get(position).producer)
            i.putExtra("description", tablelist.get(position).description)

            i.putExtra("cost", tablelist.get(position).cost)
            i.putExtra("rating", tablelist.get(position).rating)
            i.putExtra("view_count", tablelist.get(position).view_count)
            i.putExtra("created", tablelist.get(position).created)
            i.putExtra("modified", tablelist.get(position).modified)


            i.putExtra("image", tableItem.product_images)
            i.putExtra("current_img", position)

            /*
               @SerializedName("product_category_id") val product_category_id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("producer") val producer : String,
    @SerializedName("description") val description : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("rating") val rating : String,
    @SerializedName("view_count") val view_count : Int,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String,
    @SerializedName("product_images") var product_images : String
             */


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
   //   val  fav_btn:ImageView=itemView!!.findViewById(R.id.fav_btn);

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
                            it.name.contains(queryString, ignoreCase = true) || it.name.contains(
                                charSequence)
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