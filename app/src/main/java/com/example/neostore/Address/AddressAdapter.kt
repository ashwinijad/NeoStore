package com.example.neostore.Address

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.example.neostore.Address.Room.Address
import com.example.neostore.R
import java.util.*


/*class AddressAdapter(
    favoriteLists: List<Table>?,
    context: Context
) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private val favoriteLists: List<Table>
    var context: Context
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.address_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        i: Int
    ) {
        val fl: Table = favoriteLists[i]
        viewHolder.tv.setText(fl.address)
        Log.e("checkitemroom",fl.address)
    }

    override fun getItemCount(): Int {
        return favoriteLists.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.ftv_name)
        }
    }

    init {
        this.favoriteLists = favoriteLists!!
        this.context = context
    }
}*/
 class AddressAdapter(context: Context): RecyclerSwipeAdapter<AddressAdapter.ViewHolder>() {
    private var addresses: MutableList<Address> = Collections.emptyList()
     val context: Context
    var index = -1
    var listener1: ListItemClick? = null  //
   // val application = application as CustomApplication

    private var lastSelectedPosition = -1


    init {
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemViewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.address_item, viewGroup, false))

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe;
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.radio.setChecked(lastSelectedPosition == position);

        val fl: Address = addresses[position]
        viewHolder.tv.setText(fl.address)
        viewHolder.radio.setOnClickListener(View.OnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            index = position
            notifyDataSetChanged()
            val intent = Intent("custom-message1")

            intent.putExtra("item1", fl.address)
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)


        })
        if (index === position) {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        viewHolder.swipelayout.setShowMode(SwipeLayout.ShowMode.PullOut)
        viewHolder.swipelayout.addDrag(
            SwipeLayout.DragEdge.Right,
            viewHolder.swipelayout.findViewById(R.id.bottom_wrapper)
        )
        viewHolder.swipelayout.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onClose(layout: SwipeLayout) {
            }

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {
            }

            override fun onStartOpen(layout: SwipeLayout) {}
            override fun onOpen(layout: SwipeLayout) {
            }

            override fun onStartClose(layout: SwipeLayout) {}
            override fun onHandRelease(
                layout: SwipeLayout,
                xvel: Float,
                yvel: Float
            ) {
            }
        })

        viewHolder.tvDelete.setOnClickListener(View.OnClickListener { view ->
            mItemManger.removeShownLayouts(viewHolder.swipelayout)
            addresses.removeAt(position)

            val application = context as CustomApplication

            application.database.AddressDao().delete(fl)//here you delete from DB so its gone for good
            //notifyDataSetChanged() dont do this as it will reexecute onbindviewholder and skip a nice animation provided by android
            //notifyItemRemoved(position) execute only once


            notifyDataSetChanged()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, addresses.size)
            mItemManger.closeAllItems()
            Toast.makeText(
                view.context,
                "Deleted " + viewHolder.tv.getText().toString(),
                Toast.LENGTH_SHORT
            ).show()
        })

        mItemManger.bindView(viewHolder.itemView, position)
    }
    override fun getItemCount(): Int = addresses.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView
        val swipelayout: SwipeLayout
        val tvDelete:TextView
        var linearLayout: LinearLayout
        var radio: RadioButton


        init {

            tvDelete=itemView.findViewById(R.id.tvDelete)
            linearLayout =
                itemView.findViewById(R.id.linearlayoutaddressitem)
            /**/radio = itemView.findViewById(R.id.radiobutton)


            tv = itemView.findViewById(R.id.ftv_name)
            swipelayout=itemView.findViewById(R.id.swipe)

        }    }

    fun updateData(addresses:
                   MutableList<Address>) {
        this.addresses = addresses


        notifyDataSetChanged() //TODO: use ListAdapter if animations are needed
    }

}
