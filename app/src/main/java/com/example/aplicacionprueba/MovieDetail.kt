package com.example.aplicacionprueba

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.Adapters.SliderVideoAdapter
import com.example.aplicacionprueba.JsonObjets.MovieDetails_Object
import com.example.aplicacionprueba.JsonObjets.MovieVideos_Object
import com.example.aplicacionprueba.database.DataBase
import com.example.lucescamarasaccion.MoviesClient
import com.example.lucescamarasaccion.ServiceGenerator
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MovieDetail.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MovieDetail.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MovieDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var progressBar_moviedetails: ProgressBar? = null
    private var spinner: Spinner? = null
    private var opcion: TextView? = null
    private var moviedetail_layout: ScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    fun showTrailers(view: View, id: Int) {
        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getVideos(id, "39534c06f3f59b461ca70b61f782f06d", "es-ES")


        call.enqueue(object : Callback<MovieVideos_Object> {
            override fun onResponse(call: Call<MovieVideos_Object>, response: Response<MovieVideos_Object>) {
                val repos = response.body()
                movie_viewpager.adapter = SliderVideoAdapter(context!!, repos!!.results)
            }

            override fun onFailure(call: Call<MovieVideos_Object>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun showMovieDetails(view: View, id: Int){
        moviedetail_layout = view.findViewById(R.id.scrollView2)
        progressBar_moviedetails = view.findViewById(R.id.progressBar_moviedetails)

        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getMovieById(id, "39534c06f3f59b461ca70b61f782f06d", "es-ES")


        call.enqueue(object : Callback<MovieDetails_Object> {
            override fun onResponse(call: Call<MovieDetails_Object>, response: Response<MovieDetails_Object>) {
                val repos = response.body()
                var generos = ""

                if(repos != null) {
                    val tituloView: TextView = view.findViewById(R.id.movie_title)
                    val originalTitleView: TextView = view.findViewById(R.id.movie_orgininal_title)
                    val notaView: TextView = view.findViewById(R.id.movie_rating)
                    val estrenoView: TextView = view.findViewById(R.id.movie_release_data)
                    val sipnosisView: TextView = view.findViewById(R.id.movie_sipnosis)
                    val posterView: ImageView = view.findViewById(R.id.movie_poster)
                    val backdropView: ImageView = view.findViewById(R.id.movie_backdrop)
                    val genreView: TextView = view.findViewById(R.id.movie_genre)

                    try {
                        tituloView.text = repos.title
                        originalTitleView.text = repos.originalTitle
                        notaView.text = repos.voteAverage.toString()
                        estrenoView.text = repos.releaseDate
                        sipnosisView.text = repos.overview
                        for (i in 0..repos.genres!!.lastIndex) {
                            generos += " ${repos.genres!![i].name}"
                        }
                        genreView.text = generos
                        Glide.with(view).load("https://image.tmdb.org/t/p/w500${repos.posterPath}").centerCrop()
                            .into(posterView)
                        Glide.with(view).load("https://image.tmdb.org/t/p/w500${repos.backdropPath}").centerInside()
                            .into(backdropView)

                    } catch (npe: NullPointerException) {
                    }

                    //Toast.makeText(context!!, "Pelicula, cargado", Toast.LENGTH_SHORT).show()

                    progressBar_moviedetails!!.visibility = View.GONE
                    moviedetail_layout!!.visibility = View.VISIBLE
                }
                else{
                    progressBar_moviedetails!!.visibility = View.GONE
                    //Toast.makeText(context!!, "No se ha podido encontrar la película", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(MovieDetailDirections.actionMovieDetailToHomeFragment())
                }
            }
            override fun onFailure(call: Call<MovieDetails_Object>, t: Throwable) {
                Toast.makeText(context!!, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                progressBar_moviedetails!!.visibility = View.GONE
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviedetail_layout = view.findViewById(R.id.scrollView2)
        progressBar_moviedetails = view.findViewById(R.id.progressBar_moviedetails)
        spinner = view.findViewById(R.id.spinner)
        opcion = view.findViewById(R.id.textView11)

        progressBar_moviedetails!!.visibility = View.VISIBLE
        moviedetail_layout!!.visibility = View.GONE

        showMovieDetails(view, arguments!!.getInt("movie_id", 0))
        //showTrailers(view, arguments!!.getInt("movie_id", 0))

        lateinit var opciones: List<String>

        val t = Thread {
            opciones = DataBase(context!!).DaoList().getNameList()
        }
        t.start()
        t.join()


        spinner!!.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, opciones)
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //opcion!!.text = "Seleccione una lista"
                opcion!!.text = opciones[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                opcion!!.text = "Seleccione una lista"
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        listener!!.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieDetail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
