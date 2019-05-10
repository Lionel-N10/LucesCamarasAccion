package com.example.aplicacionprueba.adapters

//import com.example.aplicacionprueba.ListDetailDirections
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.database.DataBase
import com.example.aplicacionprueba.fragmentclasses.ListDetailDirections
import com.example.aplicacionprueba.jsonobjects.MovieDetails_Object
import com.example.aplicacionprueba.retrofit.MoviesClient
import com.example.aplicacionprueba.retrofit.ServiceGenerator
import com.example.aplicacionprueba.roomobjects.ListMovies
import kotlinx.android.synthetic.main.item_listadetail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListDetailAdapter(val context: Context, var values: ArrayList<Int>, var idLista: Int, var id_fragment: Int) :
    RecyclerView.Adapter<ListDetailAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_listadetail, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }

    class ViewHolder(vista: View) :
        RecyclerView.ViewHolder(vista) { //Inicializamos y asignamos los componentes de la interfaz
        var progressBar: ProgressBar? = null
        var deleteButton: Button? = null
        var card: CardView? = null
        var tituloView: TextView? = null
        var originalView: TextView? = null
        var releaseView: TextView? = null
        var rateView: TextView? = null
        var posterView: ImageView? = null

        init {
            deleteButton = vista.itemdetail_delete
            progressBar = vista.adapterList_PB
            card = vista.itemdetail_card
            tituloView = vista.itemdetail_title
            originalView = vista.itemdetail_originaltitle
            releaseView = vista.itemdetail_release
            rateView = vista.itemdetail_imbd
            posterView = vista.itemdetail_poster
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //
        val item = values.get(position)

        Log.d("id Lista: ", item.toString())

        //Realizamos una consulta a la api para obtener los datos de las peliculas usando el 1d que hemos sacado de la base de datos
        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getMovieById(item, "39534c06f3f59b461ca70b61f782f06d", "es-ES")


        call.enqueue(object : Callback<MovieDetails_Object> {
            override fun onResponse(call: Call<MovieDetails_Object>, response: Response<MovieDetails_Object>) {
                val repos = response.body()

                //Ocultamos los items y mostramos la barra de progresion

                holder.progressBar!!.visibility = View.VISIBLE
                holder.card!!.visibility = View.INVISIBLE

                //Asignamos a cada componente la informacion que va a mostrar
                holder.tituloView?.text = repos!!.title
                holder.rateView?.text = R.string.imdbrating.toString() + repos.voteAverage.toString()
                holder.releaseView?.text = "${R.string.releasedate}${repos.releaseDate.toString()}"
                holder.originalView?.text = repos.originalTitle
                Glide.with(context).load("https://image.tmdb.org/t/p/w500${repos.posterPath}")
                    .centerCrop()
                    .thumbnail(0.5f)
                    .into(holder.posterView!!)
                holder.card!!.setOnClickListener {
                    when (id_fragment) {
                        1 -> Navigation.findNavController(it).navigate(
                            ListDetailDirections.actionListDetailToMovieDetail(
                                repos.id!!
                            )
                        )
                    }
                }
                holder.progressBar!!.visibility = View.GONE
                holder.card!!.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<MovieDetails_Object>, t: Throwable) {
                Toast.makeText(context, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

        //Con este metodo eliminamos las listas
        holder.deleteButton!!.setOnClickListener {
            GlobalScope.launch {
                DataBase(context).DaoMovies().deleteLista(
                    ListMovies(
                        idLista,
                        item
                    )
                )
            }
            values.removeAt(position)
            notifyDataSetChanged()
        }
    }
}