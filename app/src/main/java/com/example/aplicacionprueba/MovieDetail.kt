package com.example.aplicacionprueba

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lucescamarasaccion.Movies
import com.example.lucescamarasaccion.MoviesClient
import com.example.lucescamarasaccion.ServiceGenerator
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var progressBar_moviedetails: ProgressBar? = null
    private var moviedetail_layout: ScrollView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviedetail_layout = view.findViewById(R.id.scrollView2)
        progressBar_moviedetails = view.findViewById(R.id.progressBar_moviedetails)


        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.GetTopRatedMovies("39534c06f3f59b461ca70b61f782f06d", "es-ES", 1)


        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val repos = response.body()

                val tituloView: TextView
                val originalTitleView: TextView
                val notaView: TextView
                val estrenoView: TextView
                val sipnosisView: TextView
                val posterView: ImageView

                tituloView =view.findViewById(R.id.movie_title)
                originalTitleView = view.findViewById(R.id.movie_orgininal_title)
                notaView= view.findViewById(R.id.movie_rating)
                estrenoView = view.findViewById(R.id.movie_release_data)
                sipnosisView = view.findViewById(R.id.movie_sipnosis)
                posterView = view.findViewById(R.id.movie_poster)

                tituloView.text = repos!!.results!![2].title
                originalTitleView.text = repos.results!![2].originalTitle
                notaView.text = repos.results!![2].voteAverage.toString()
                estrenoView.text = repos.results!![2].releaseDate
                sipnosisView.text = repos.results!![2].overview
                Glide.with(view).load("https://image.tmdb.org/t/p/w500${repos.results!![2].posterPath}").into(posterView)

               //Toast.makeText(context!!, "Pelicula, cargado", Toast.LENGTH_SHORT).show()

                progressBar_moviedetails!!.visibility = View.GONE
                moviedetail_layout!!.visibility = View.VISIBLE
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context!!, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
                progressBar_moviedetails!!.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
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
