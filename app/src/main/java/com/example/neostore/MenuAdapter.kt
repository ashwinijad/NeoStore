package com.example.neostore

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import nl.psdcompany.duonavigationdrawer.views.DuoOptionView


internal class MenuAdapter(options: ArrayList<String>) : BaseAdapter() {
    private var mOptions: ArrayList<String> = ArrayList()
    private val mOptionViews: ArrayList<DuoOptionView> = ArrayList()
    override fun getCount(): Int {
        return mOptions.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        return mOptions[position]
    }

    fun setViewSelected(position: Int, selected: Boolean) {

        // Looping through the options in the menu
        // Selecting the chosen option
        for (i in 0 until mOptionViews.size) {
            if (i == position) {
                mOptionViews[i].isSelected = selected
            } else {
                mOptionViews[i].isSelected = !selected
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

     fun getView(position: Int, convertView: DuoOptionView?, parent: ViewGroup): DuoOptionView {
        val option = mOptions[position]

        // Using the DuoOptionView to easily recreate the demo
        val optionView: DuoOptionView
        optionView = if (convertView == null) {
            DuoOptionView(parent.context)
        } else {
            convertView
        }

        // Using the DuoOptionView's default selectors
        optionView.bind(option, null, null)

        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView)
        return optionView
    }

    init {
        mOptions = options
    }
}