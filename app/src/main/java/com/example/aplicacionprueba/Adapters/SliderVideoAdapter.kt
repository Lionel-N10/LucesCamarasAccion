package com.example.aplicacionprueba.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.example.aplicacionprueba.JsonObjets.Video_result
import com.example.aplicacionprueba.R

class SliderVideoAdapter(var context: Context, videos: List<Video_result>?) : PagerAdapter() {
    private val videos: List<Video_result>?
    lateinit var inflater: LayoutInflater

    init {
        this.videos = videos!!
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val videoView: VideoView
        val item = videos!!.get(position)

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.slider_video_item, container, false)
        videoView = view.findViewById(R.id.videoView2)
        videoView.setVideoPath("https://www.youtube.com/watch?v=${item.id}")
        videoView.pause()
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as LinearLayout)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj as LinearLayout


    override fun getCount(): Int {
        return videos!!.size
    }
}