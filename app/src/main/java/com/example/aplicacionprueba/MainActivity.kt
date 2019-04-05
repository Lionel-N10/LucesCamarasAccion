package com.example.aplicacionprueba

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), Home_Fragment.OnFragmentInteractionListener, List_TopRated.OnFragmentInteractionListener, MovieDetail.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(host_fragment).navigateUp()){
            false -> moveTaskToBack(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bFav = R.id.app_bar_fav
        var bBuscar = R.id.app_bar_search
        var bOpciones = R.id.app_bar_settings

        var vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        var vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)

        toolbar.inflateMenu(R.menu.menu_toolbar)

        bar.replaceMenu(R.menu.menu_bottombar_home)

        bar.setOnMenuItemClickListener { MenuItem ->
            when(MenuItem.itemId){
                bFav -> Toast.makeText(this,"Boton favorito pulsado", Toast.LENGTH_SHORT).show()
                bBuscar -> Toast.makeText(this,"Boton busqueda pulsado", Toast.LENGTH_SHORT).show()
                bOpciones -> Toast.makeText(this,"Boton opciones pulsado", Toast.LENGTH_SHORT).show()
            }
             true
        }
    }
}
