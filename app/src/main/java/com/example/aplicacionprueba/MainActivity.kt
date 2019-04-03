package com.example.aplicacionprueba

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity(), Home_Fragment.OnFragmentInteractionListener, List_TopRated.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        var vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)
    }
}
