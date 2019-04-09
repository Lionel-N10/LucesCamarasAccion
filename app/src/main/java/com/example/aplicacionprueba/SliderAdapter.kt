package com.example.aplicacionprueba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.lucescamarasaccion.Movies

class SliderAdapter : PagerAdapter {
    var context: Context
    var images: Movies
    lateinit var inflater: LayoutInflater

    constructor(context: Context, images: Movies?) : super() {
        this.context = context
        this.images = images!!
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.slider_image_item, container, false)
        image = view.findViewById(R.id.imageView)
        Glide.with(view).load("https://image.tmdb.org/t/p/w400${images.results!![position].posterPath}").centerCrop()
            .into(image)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as LinearLayout


    override fun getCount(): Int {
        return images.totalResults!!
    }


}