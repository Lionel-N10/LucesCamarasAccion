package com.example.aplicacionprueba.fragmentclasses

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.firebase.moviesFB
import com.example.aplicacionprueba.jsonobjects.MovieDetails_Object
import com.example.aplicacionprueba.jsonobjects.MovieVideos_Object
import com.example.aplicacionprueba.retrofit.MoviesClient
import com.example.aplicacionprueba.retrofit.ServiceGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        val videoView = view.findViewById<WebView>(R.id.webView)
        val card_trailers = view.findViewById<CardView>(R.id.movie_card_trailers)

        call.enqueue(object : Callback<MovieVideos_Object> {
            override fun onResponse(call: Call<MovieVideos_Object>, response: Response<MovieVideos_Object>) {
                val repos = response.body()
                //movie_viewpager.adapter = SliderVideoAdapter(context!!, repos!!.results)

                if (repos!!.results!!.isNotEmpty()) { //Si se encuentran trailers cargamos el principal en el webview

                    val frameVideo =
                        "<!DOCTYPE HTML> <html><body style=\"margin:0 0 0 0; padding:0 0 0 0;\"> <iframe width=\"350\" height=\"220\" src=\"https://www.youtube.com/embed/${repos.results!![0].key}\" frameborder=\"0\"></iframe> </body> </html>"

                    videoView.settings.javaScriptEnabled = true

                    videoView.loadData(frameVideo, "text/html", "UTF-8")

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        videoView.webChromeClient
                    }

                }else{
                    card_trailers.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MovieVideos_Object>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun showMovieDetails(view: View, id: Int) {
        moviedetail_layout = view.findViewById(R.id.scrollView2)
        progressBar_moviedetails = view.findViewById(R.id.progressBar_moviedetails)

        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getMovieById(id, "39534c06f3f59b461ca70b61f782f06d", "es-ES")


        call.enqueue(object : Callback<MovieDetails_Object> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<MovieDetails_Object>, response: Response<MovieDetails_Object>) {
                val repos = response.body()
                var generos = ""

                if (repos != null) {
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
                        notaView.text = "${getString(R.string.imdbrating)}: ${repos.voteAverage.toString()}"
                        estrenoView.text = "${getString(R.string.releasedate)}: ${repos.releaseDate}"
                        //Controlamos el scroll de la sipnosis y si esta vacía o no
                        sipnosisView.text = repos.overview
                        sipnosisView.movementMethod = ScrollingMovementMethod()
                        if(sipnosisView.text == ""){
                            movie_card_overview.visibility = View.GONE
                        }

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
                } else {
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
        opcion = view.findViewById(R.id.textView11)

        progressBar_moviedetails!!.visibility = View.VISIBLE
        moviedetail_layout!!.visibility = View.GONE

        val movie_id = arguments!!.getInt("movie_id", 0)

        showMovieDetails(view, movie_id)
        showTrailers(view, movie_id)

        val like_button = view.findViewById<Button>(R.id.movie_like)

        var peliculas: List<Int>? = null

        val Auth = FirebaseAuth.getInstance()
        val currentUser = Auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val ref = database.reference

        val objmovieId = moviesFB(movie_id.toString())

        like_button.setOnClickListener {
            ref.child("Me gusta").child(currentUser!!.uid).child(movie_id.toString()).setValue(objmovieId)

            Toast.makeText(context!!, "'${movie_title.text}' ha sido añadida a favoritas", Toast.LENGTH_SHORT).show()
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
