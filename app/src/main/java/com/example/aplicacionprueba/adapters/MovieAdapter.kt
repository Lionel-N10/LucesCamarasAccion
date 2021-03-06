package com.example.aplicacionprueba.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.fragmentclasses.PopularMoviesDirections
import com.example.aplicacionprueba.fragmentclasses.TopRatedMoviesDirections
import com.example.aplicacionprueba.fragmentclasses.UpcomingMoviesDirections
import com.example.aplicacionprueba.fragmentclasses.search_moviesDirections
import com.example.lucescamarasaccion.Result
import kotlinx.android.synthetic.main.list_item_pagination.view.*


//Esta clase es el adaptador para cargar los fragments de TopRated, MostPopular, Upcoming, Busqueda...

class MovieAdapter(val context: Context, var values: List<Result>?, var id_fragment: Int) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pagination, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){

        var card: CardView? = null
        var tituloView: TextView? = null
        var notaView: TextView? = null
        var estrenoView: TextView? = null
        var posterView: ImageView? = null
        init {
            tituloView = vista.movie_title
            notaView = vista.movie_rating
            estrenoView = vista.movie_release_data
            posterView = vista.movie_poster
            card = vista.movie_card
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.tituloView?.text = item?.title
        holder.estrenoView?.text = "Fecha de estreno: "+ item?.releaseDate
        holder.notaView?.text = "Nota IMDb: ${item?.voteAverage.toString()}"
        holder.itemView.setOnClickListener { }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!!.posterPath}").centerCrop().thumbnail(1f)
            .into(holder.posterView!!)
        holder.card!!.setOnClickListener {
            val movieId = item.id
            when (id_fragment) {
                1 -> Navigation.findNavController(it).navigate(
                    TopRatedMoviesDirections.actionListTopRatedToMovieDetail(movieId!!)
                )
                2 -> Navigation.findNavController(it).navigate(
                    search_moviesDirections.actionSearchMoviesToMovieDetail(movieId!!)
                )
                3 -> Navigation.findNavController(it).navigate(
                    PopularMoviesDirections.actionPopularMoviesToMovieDetail(movieId!!)
                )
                4 -> Navigation.findNavController(it).navigate(
                    UpcomingMoviesDirections.actionUpcomingMoviesToMovieDetail(movieId!!)
                )
            }
        }
    }
}

