package com.example.neostore.ProductDetails

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.neostore.R


class  Custom_detail_Adapter(
    context: Context,
    dataList: List<Product_images_response>
) :
    RecyclerView.Adapter<Custom_detail_Adapter.CustomViewHolder>() {
    private val dataList: List<Product_images_response>
    private val context: Context

    // If you want first item of recyclerview selected by defualt
    private var selectedPos: Int = 0
    //private val selectedItems = SparseBooleanArray()

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mView: View

         val avatar: ImageView
val relativeitem:RelativeLayout

        init {
            mView = itemView

            avatar = mView.findViewById(R.id.view3)
relativeitem=mView.findViewById(R.id.relativeitem)

            }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.product_detail_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
      ///  holder.relativeitem.setBackgroundColor(Color.parseColor("#000000"));
        holder.relativeitem.setSelected(selectedPos == position);
     Glide.with(context).load(dataList[position].image)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.avatar)


        holder.avatar.setOnClickListener(View.OnClickListener {
                notifyItemChanged(selectedPos)
                selectedPos = position
                notifyItemChanged(selectedPos)

            //val i = Intent(context, Product_details::class.java)
            // i.putExtra("photo_id", thisSpacecraft.getId())
            ///  i.putExtra("Title", dataList.get(position).image)
            // context.startActivity(i)
            val intent = Intent("custom-message")
            //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
            //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
            // intent.putExtra("quantity", qty)
            intent.putExtra("item", dataList.get(position).image)
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
        //return (dataList == null) ? 0 : dataList.size();
    }

    init {
        this.context = context
        this.dataList = dataList
    }


}

