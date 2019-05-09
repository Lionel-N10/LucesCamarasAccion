package com.example.aplicacionprueba

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionprueba.Adapters.MovieAdapter
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
 * [UpcomingMovies.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UpcomingMovies.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UpcomingMovies : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pbCargando = view.findViewById(R.id.progress_bar_upcoming) as ProgressBar
        val rvUpcoming = view.findViewById(R.id.rv_upcoming) as RecyclerView

        val client = ServiceGenerator.createService(MoviesClient::class.java)

        val call = client.GetUpcoming("39534c06f3f59b461ca70b61f782f06d", "es-ES")
        call.request().url().host()

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val repos = response.body()

                rvUpcoming.layoutManager = GridLayoutManager(activity, 2)
                rvUpcoming.adapter = MovieAdapter(context!!, repos!!.results, 1)

                pbCargando.visibility = View.GONE
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context!!, "Error al cargar, comprueba tu conexión a internet", Toast.LENGTH_SHORT)
                    .show()
                t.printStackTrace()
                pbCargando.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
         * @return A new instance of fragment UpcomingMovies.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpcomingMovies().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
