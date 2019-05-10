package com.example.aplicacionprueba.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.fragmentclasses.Home_FragmentDirections
import com.example.aplicacionprueba.fragmentclasses.PopularMoviesDirections
import com.example.aplicacionprueba.fragmentclasses.TopRatedMoviesDirections
import com.example.aplicacionprueba.fragmentclasses.search_moviesDirections
import com.example.lucescamarasaccion.Result
import kotlinx.android.synthetic.main.item_mostpopular.view.*


//Esta clase se encarga de cargar las peliculas en los expositores con recycler view del Home
class HomeExhibitorAdapter(val context: Context, var values: List<Result>?, var id_fragment: Int) :
    RecyclerView.Adapter<HomeExhibitorAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_mostpopular, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) { //Inicializamos los componentes y los asignamos
        var card: CardView? = null
        var posterView: ImageView? = null

        init {
            posterView = vista.mostpopular_poster
            card = vista.cardViewMostPopu
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)

        //Cargamos las imagenes
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!!.posterPath}").centerCrop().thumbnail(0.5f)
            .into(holder.posterView!!)

        //Dependiendo del codigo que nos pase la clase que llama a este adaptador, utilizaremos el Navigation correspondiente para cargar el fragment MovieDetail de la pelicula seleccionada
        holder.card!!.setOnClickListener {
            val movieId = item.id
            when (id_fragment) {
                1 -> Navigation.findNavController(it).navigate(
                    TopRatedMoviesDirections.actionListTopRatedToMovieDetail(
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
