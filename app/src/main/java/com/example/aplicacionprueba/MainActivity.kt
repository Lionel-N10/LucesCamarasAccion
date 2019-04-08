package com.example.aplicacionprueba

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Home_Fragment.OnFragmentInteractionListener, List_TopRated.OnFragmentInteractionListener, MovieDetail.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        Log.d("Cosas", "mas cosas")

        toolbar.inflateMenu(R.menu.menu_toolbar)
        val searchItem = menu!!.findItem(R.id.app_bar_search)

        val vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        val vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)

        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            /*val editext = searchView.findViewById<EditText>(R.id.search_src_text)
            editext.hint = "Busca aqui..."*/

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(this@MainActivity, "Busqueda realizada", Toast.LENGTH_SHORT).show()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        var search = newText.toLowerCase()
                    }else{
                        Toast.makeText(this@MainActivity, "Texto modificado", Toast.LENGTH_LONG).show()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(host_fragment).navigateUp()){
            false -> moveTaskToBack(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val bFav = R.id.app_bar_fav
        //var bBuscar = R.id.app_bar_search
        val bOpciones = R.id.app_bar_settings




        //toolbar.inflateMenu(R.menu.menu_toolbar)

        bar.replaceMenu(R.menu.menu_bottombar_home)

        bar.setOnMenuItemClickListener { MenuItem ->
            when(MenuItem.itemId){
                bFav -> Toast.makeText(this,"Boton favorito pulsado", Toast.LENGTH_SHORT).show()
                //bBuscar -> Toast.makeText(this,"Boton busqueda pulsado", Toast.LENGTH_SHORT).show()
                bOpciones -> Toast.makeText(this,"Boton opciones pulsado", Toast.LENGTH_SHORT).show()
            }
             true
        }

    }



}
