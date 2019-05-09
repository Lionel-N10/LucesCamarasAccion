package com.example.aplicacionprueba.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.example.aplicacionprueba.JsonObjets.Video_result
import com.example.aplicacionprueba.R


class SliderVideoAdapter(var context: Context, videos: List<Video_result>?) : PagerAdapter() {
    private val videos: List<Video_result>?
    lateinit var inflater: LayoutInflater

    lateinit var videoView: WebView

    init {
        this.videos = videos!!
    }

    @SuppressLint("NewApi")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = videos!!.get(position)

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.slider_video_item, container, false)
        videoView = view.findViewById(R.id.webView)

        var frameVideo = "<html><body><iframe{margin:0;padding:0;} width=350 height=250 src=https://www.youtube.com/embed/${item.key} frameborder="+0+" allowfullscreen></iframe></body></html>"


        videoView.webChromeClient
        videoView.settings.javaScriptEnabled = true

        videoView.loadData("https://www.youtube.com/watch?v=${item.key}", "text/Webm", "UTF-8")
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