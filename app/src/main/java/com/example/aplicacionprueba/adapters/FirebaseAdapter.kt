package com.example.aplicacionprueba.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionprueba.R
import com.example.aplicacionprueba.firebase.FireBaseData
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase


val database = FirebaseDatabase.getInstance().getReference("Usuario1")

val options = FirebaseRecyclerOptions.Builder<FireBaseData>().setQuery(database, FireBaseData::class.java).build()

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
//FirebaseRecyclerAdapter<List<ListFB>, NoteAdapter.ViewHolder>()