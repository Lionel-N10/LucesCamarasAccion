package com.example.aplicacionprueba

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Home_Fragment.OnFragmentInteractionListener,
    List_TopRated.OnFragmentInteractionListener, MovieDetail.OnFragmentInteractionListener,
    search_movies.OnFragmentInteractionListener, PopularMovies.OnFragmentInteractionListener,
    Login.OnFragmentInteractionListener, Registro.OnFragmentInteractionListener,
    MisListas.OnFragmentInteractionListener, ListDetail.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        toolbar.inflateMenu(R.menu.menu_toolbar)
        val searchItem = menu!!.findItem(R.id.app_bar_search)

        val vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        val vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)

        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    val title = query

                    val pack = bundleOf("titulo" to title)

                    NavHostFragment.findNavController(host_fragment).navigate(R.id.Search_Movies, pack)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        var search = newText.toLowerCase()
                    }else{
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         Navigation.findNavController()
         return super.onOptionsItemSelected(item)
     }*/

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(host_fragment).navigateUp()){
            false -> moveTaskToBack(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val bPopular = R.id.app_bar_popular
        val bTop_Rated = R.id.app_bar_toprated
        val bListas = R.id.app_bar_settings
        val bUser = R.id.app_bar_user
        val bHome = R.id.app_bar_home

        bar.replaceMenu(R.menu.menu_bottombar_home)

        bar.setOnMenuItemClickListener { MenuItem ->
            when(MenuItem.itemId){

                bHome -> NavHostFragment.findNavController(host_fragment).navigate(R.id.home_fragment)
                bPopular -> NavHostFragment.findNavController(host_fragment).navigate(R.id.popularMovies)
                bTop_Rated -> NavHostFragment.findNavController(host_fragment).navigate(R.id.list_TopRated)
                bUser -> NavHostFragment.findNavController(host_fragment).navigate(R.id.login)
                bListas -> NavHostFragment.findNavController(host_fragment).navigate(R.id.misListas)
            }
             true
        }

    }


}
