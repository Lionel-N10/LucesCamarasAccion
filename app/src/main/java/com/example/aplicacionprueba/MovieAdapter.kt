package com.example.aplicacionprueba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.JsonObjets.GenresResponse
import com.example.lucescamarasaccion.MoviesClient
import com.example.lucescamarasaccion.Result
import com.example.lucescamarasaccion.ServiceGenerator
import kotlinx.android.synthetic.main.list_item_pagination.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieAdapter(val context: Context, var values: List<Result>?, var id_fragment: Int) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null
    var allGenres: ArrayList<String>? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pagination, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    interface ClickListener {
        fun onClick(position: Int, view: View)
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
            card = vista.cardView
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.tituloView?.text = item?.title
        holder.estrenoView?.text = item?.releaseDate
        holder.notaView?.text = item?.voteAverage.toString()
        holder.itemView.setOnClickListener { }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!!.posterPath}").centerCrop().thumbnail(0.5f)
            .into(holder.posterView!!)
        holder.card!!.setOnClickListener {
            val movieId = item.id
            //Toast.makeText(context, "Item $position, pulsado. Id = $movieId", Toast.LENGTH_SHORT).show()

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
            }
        }
    }


    fun ObtenerGeneros(genreIds: List<Int>?) {

        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getGenres("39534c06f3f59b461ca70b61f782f06d", "es-ES")


        call.enqueue(object : Callback<GenresResponse> {
            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                val repos = response.body()

                for (i in 0..genreIds!!.lastIndex) {
                    if (repos!!.genres!![i].id == genreIds[i]) {
                        allGenres!!.add(repos.genres!![i].name!!)
                    }
                }
                Toast.makeText(context, "MejorValoradas, cargado", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

