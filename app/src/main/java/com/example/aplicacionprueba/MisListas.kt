package com.example.aplicacionprueba

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionprueba.Adapters.MisListasAdapter
import com.example.aplicacionprueba.JsonObjets.FireBaseData
import com.example.aplicacionprueba.JsonObjets.Lista
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MisListas.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MisListas.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MisListas : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var listView: RecyclerView? = null
    //private var fbrecycler:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listas : List<Lista>? = null

        listView = view.findViewById(R.id.rv_mislitas)

        /*val t = Thread{
            listas = DataBase(context!!).DaoList().getLista()
        }
        t.start()
        t.join()*/

        val database = FirebaseDatabase.getInstance().getReference("Usuario1")//Obtenemos las listas

       database.addValueEventListener( object: ValueEventListener {
             override fun onDataChange(dataSnapshot: DataSnapshot) {

                 val options = FirebaseRecyclerOptions.Builder<FireBaseData>().setQuery(com.example.aplicacionprueba.Adapters.database, FireBaseData::class.java).build()

                 class FirebaseAdapter: FirebaseRecyclerAdapter<FireBaseData, FirebaseAdapter.ViewHolder>(options){

                     override fun onBindViewHolder(holder: ViewHolder, position: Int, values: FireBaseData) {

                         val item = values.ListFB
                         println("prueba")

                         Log.d("FirebaseAdapter: ", item!![position].listName)

                         holder.titleView.text = item[position].listName
                         holder.posicionView.text = (position+1).toString()
                         holder.countView.text = item.size.toString()
                     }

                     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
                         return ViewHolder(view)
                     }

                     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                         var titleView: TextView
                         var posicionView: TextView
                         var countView: TextView

                         init {
                             titleView = itemView.findViewById(R.id.lista_titulo)
                             posicionView = itemView.findViewById(R.id.lista_position)
                             countView = itemView.findViewById(R.id.lista_movie_count)
                         }
                     }
                 }

                 listView!!.layoutManager = LinearLayoutManager(activity)
                 listView!!.adapter = FirebaseAdapter()
                 FirebaseAdapter().startListening()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Error!", databaseError.toException().toString())
            }
        })


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_listas, container, false)
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
         * @return A new instance of fragment MisListas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MisListas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
