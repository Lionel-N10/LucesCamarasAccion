package com.example.aplicacionprueba

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.lucescamarasaccion.Result
import kotlinx.android.synthetic.main.list_item_pagination.view.*

class MovieAdapter(val context: Context, var values: List<Result>?): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pagination, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var tituloView: TextView? = null
        // var generoView: TextView? = null
        var notaView: TextView? = null
        var estrenoView: TextView? = null
        var posterView: ImageView? = null

        init {
            tituloView = vista.movie_title
            //generoView = vista.movie_genre
            notaView = vista.movie_rating
            estrenoView = vista.movie_release_data
            posterView = vista.movie_poster
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.tituloView?.text = item?.title
        holder.estrenoView?.text = item?.releaseDate
        holder.notaView?.text = item?.voteAverage.toString()
        //holder.generoView?.text = "Accion, Comedia"
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!!.posterPath}").thumbnail(0.2f).into(holder.posterView!!)
    }
}

