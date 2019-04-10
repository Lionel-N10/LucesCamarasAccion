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

class SliderAdapter(var context: Context, images: Movies?) : PagerAdapter() {
    var images: Movies
    lateinit var inflater: LayoutInflater
    private val MAX_VALUE = 200

    init {
        this.images = images!!
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.slider_image_item, container, false)
        image = view.findViewById(R.id.imageView)

        Glide.with(view).load("https://image.tmdb.org/t/p/w500${images.results!![position].posterPath}").centerCrop()
            .into(image)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as LinearLayout)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj as LinearLayout


    override fun getCount(): Int {
        return images.totalResults!! * MAX_VALUE
    }


}