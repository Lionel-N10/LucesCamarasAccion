package com.example.lucescamarasaccion


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.aplicacionprueba.R

class MovieAdapter(val context: Context, var values: List<Result>?): BaseAdapter() /*ArrayAdapter<List<Result>>(context, R.layout.list_item_pagination, values!!.size)*/ {
    override fun getItem(position: Int): Any {
       return values!![position]
    }

    override fun getItemId(position: Int): Long {
        return values!!.size.toLong()
    }

    override fun getCount(): Int {
        return values!!.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView

        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.list_item_pagination, parent, false)
        }

        var tituloView: TextView
        var generoView: TextView
        var notaView: TextView
        var estrenoView: TextView
        var posterView: ImageView

        tituloView = row!!.findViewById(R.id.list_item_pagination_text)
       //generoView = row.findViewById(R.id.item_movie_genre)
        notaView = row.findViewById(R.id.item_movie_rating)
        estrenoView = row.findViewById(R.id.item_movie_release_date)



        val item = this.values

        val titulo = item!![position].title
        //val genero = item!![position]
        val nota = item!![position].voteAverage
        val estreno = item!![position].releaseDate


        tituloView.text = titulo
        notaView.text = nota.toString()
        estrenoView.text = estreno
        Glide.with(context).load("https://image.tmdb.org/t/p/w500${item!![position].posterPath}").thumbnail(0.2f).into(row.findViewById(R.id.item_movie_poster) as ImageView)

        return row
    }
}



/*class MovieAdapter(var values: Movie?, context: Context):
    ListAdapter<>(context, R.layout.list_item_pagination, values) {

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var row: View? = convertView

        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.list_item_pagination, parent, false)
        }

        val textView = row!!.findViewById(R.id.list_item_pagination_text) as TextView

        val item = values[position]
        val message = item.name
        textView.setText(message)

        return row
    }

}*/