package com.example.aplicacionprueba

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionprueba.Adapters.MostPopularRV_Adapter
import com.example.aplicacionprueba.Adapters.SliderImageAdapter
import com.example.aplicacionprueba.Adapters.UpcomingRV_Adapter
import com.example.lucescamarasaccion.Movies
import com.example.lucescamarasaccion.MoviesClient
import com.example.lucescamarasaccion.ServiceGenerator
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
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
 * [Home_Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Home_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Home_Fragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var RVUpcoming: RecyclerView? = null
    private var RVMostPopular: RecyclerView? = null

    private var progresBar: ProgressBar? = null
    private var home_layout: ScrollView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)

        bottomBar.visibility = View.VISIBLE//Hacemos visible la barra de navegación. Por defecto está invisible hasta que inicias sesion

        progresBar = view.findViewById(R.id.home_PB)
        home_layout = view.findViewById(R.id.home_SV)


        //Arrancamos los metodos para cargar los expositores del home
        showNowPlaying()
        showUpComing(view)
        showPopular(view)

        //Los click listener para los expositores, cada uno lleva a su pantalla
        text_popular.setOnClickListener {
            Navigation.findNavController(it).navigate(Home_FragmentDirections.actionHomeFragmentToPopularMovies())
        }
        text_upcoming.setOnClickListener {
            Navigation.findNavController(it).navigate(Home_FragmentDirections.actionHomeFragmentToUpcomingMovies())
        }
        text_releases.setOnClickListener {
            Navigation.findNavController(it).navigate(Home_FragmentDirections.actionHomeFragmentToReleasesMovies())
        }


        if (showNowPlaying() && showUpComing(view) && showPopular(view)) {
            progresBar!!.visibility = View.GONE
            home_layout!!.visibility = View.VISIBLE
        } else {
            progresBar!!.visibility = View.GONE
            Toast.makeText(context!!, "Erros al cargar", Toast.LENGTH_SHORT).show()
        }
    }

    fun showNowPlaying(): Boolean {
        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getNowPlaying("39534c06f3f59b461ca70b61f782f06d", "es-ES", 1)

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val repos = response.body()
                try {
                    viewPager.adapter = SliderImageAdapter(context!!, repos!!.results)
                } catch (ise: IllegalStateException) {
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context!!, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
        return true

    }

    fun showUpComing(view: View): Boolean {
        RVUpcoming = view.findViewById(R.id.recyclerView)

        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.GetUpcoming("39534c06f3f59b461ca70b61f782f06d", "es-ES")

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val repos = response.body()
                try {
                    RVUpcoming!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    RVUpcoming!!.adapter =
                        UpcomingRV_Adapter(context!!, repos!!.results, 4)
                } catch (ise: IllegalStateException) {
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context!!, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

        return true
    }

    fun showPopular(view: View): Boolean {
        RVMostPopular = view.findViewById(R.id.recyclerView2)

        val client = ServiceGenerator.createService(MoviesClient::class.java)
        val call = client.getPopularMovies("39534c06f3f59b461ca70b61f782f06d", "es-ES",1)

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val repos = response.body()
                try {
                    RVMostPopular!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    RVMostPopular!!.adapter =
                        MostPopularRV_Adapter(context!!, repos!!.results, 5)

                } catch (ise: IllegalStateException) {
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context!!, "Comprueba tu conexión a internet", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as AppCompatActivity).supportActionBar!!.show()
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
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
