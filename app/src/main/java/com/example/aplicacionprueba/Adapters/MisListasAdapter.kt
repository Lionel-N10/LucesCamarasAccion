package com.example.aplicacionprueba.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionprueba.JsonObjets.ListFB
import com.example.aplicacionprueba.MisListasDirections
import com.example.aplicacionprueba.R
import kotlinx.android.synthetic.main.item_lista.view.*

class MisListasAdapter(val context: Context, var values: List<ListFB>?, var id_fragment: Int) :
    RecyclerView.Adapter<MisListasAdapter.ViewHolder>() {

    var viewHolder: ViewHolder? = null

    override fun getItemCount(): Int {
        return values!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
        viewHolder = ViewHolder(vista)

        return viewHolder!!
    }


    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){

        var card: CardView? = null
        var tituloView: TextView? = null
        var totalView: TextView? = null
        var idView: TextView? = null
        var posicionView: TextView? = null

        init {
            tituloView = vista.lista_titulo
            totalView = vista.lista_movie_count
            posicionView = vista.lista_position
            idView = vista.idLista
            card = vista.lista_card
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        val listaId = item!!.listId

        holder.tituloView!!.text = item.listName
        holder.posicionView!!.text = (position+1).toString()
        holder.idView!!.text = item.listId.toString()
        holder.card!!.setOnClickListener {
            when (id_fragment) {
                1 -> Navigation.findNavController(it).navigate(MisListasDirections.actionMisListasToListDetail(listaId!!))
            }
        }
    }
}