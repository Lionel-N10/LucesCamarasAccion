package com.example.aplicacionprueba.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.*
import com.example.lucescamarasaccion.Result
import kotlinx.android.synthetic.main.item_upcoming.view.*

class UpcomingRV_Adapter(val context: Context, var values: List<Result>?, var id_fragment: Int) :
    RecyclerView.Adapter<UpcomingRV_Adapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        var card: CardView? = null
        var posterView: ImageView? = null

        init {
            posterView = vista.upcoming_poster
            card = vista.cardView3
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.itemView.setOnClickListener { }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!!.posterPath}").centerCrop().thumbnail(0.5f)
            .into(holder.posterView!!)
        holder.card!!.setOnClickListener {
            val movieId = item.id
            when (id_fragment) {
                1 -> Navigation.findNavController(it).navigate(
                    List_TopRatedDirections.actionListTopRatedToMovieDetail(
                        movieId!!
                    )
                )
                2 -> Navigation.findNavController(it).navigate(
                    search_moviesDirections.actionSearchMoviesToMovieDetail(
                        movieId!!
                    )
                )
                3 -> Navigation.findNavController(it).navigate(
                    PopularMoviesDirections.actionPopularMoviesToMovieDetail(
                        movieId!!
                    )
                )
                4 -> Navigation.findNavController(it).navigate(
                    Home_FragmentDirections.actionHomeFragmentToMovieDetail(
                        movieId!!
                    )
                )
                5 -> Navigation.findNavController(it).navigate(
                    Home_FragmentDirections.actionHomeFragmentToMovieDetail(
                        movieId!!
                    )
                )
            }
        }
    }
}
