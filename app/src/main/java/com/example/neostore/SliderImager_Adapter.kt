package com.example.neostore

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class SliderImager_Adapter(context: Context, IMAGES: ArrayList<Int>) :
    PagerAdapter() {
    private val IMAGES: ArrayList<Int>
    private val inflater: LayoutInflater
    private val context: Context
    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return IMAGES.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout: View = inflater.inflate(R.layout.slider_items, view, false)!!
        val imageView: ImageView = imageLayout
            .findViewById(R.id.image) as ImageView
        imageView.setImageResource(IMAGES[position])
        view.addView(imageLayout, 0)
        return imageLayout
    }

   override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun restoreState(
        state: Parcelable?,
        loader: ClassLoader?
    ) {
    }

    override fun saveState(): Parcelable? {
        return null
    }

    init {
        this.context = context
        this.IMAGES = IMAGES
        inflater = LayoutInflater.from(context)
    }
}