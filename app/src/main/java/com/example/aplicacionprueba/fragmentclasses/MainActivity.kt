package com.example.aplicacionprueba.fragmentclasses

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.aplicacionprueba.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    Home_Fragment.OnFragmentInteractionListener,
    TopRatedMovies.OnFragmentInteractionListener,
    MovieDetail.OnFragmentInteractionListener,
    search_movies.OnFragmentInteractionListener,
    PopularMovies.OnFragmentInteractionListener,
    Login.OnFragmentInteractionListener,
    Registro.OnFragmentInteractionListener,
    MisListas.OnFragmentInteractionListener,
    ListDetail.OnFragmentInteractionListener,
    UpcomingMovies.OnFragmentInteractionListener,
    ReleasesMovies.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        setToolbar(menu)
        return super.onCreateOptionsMenu(menu)
    }


    //Creamos la Toolbar y asigmanos sus items.
    fun setToolbar(menu: Menu?){
        toolbar.inflateMenu(R.menu.menu_toolbar)
        val searchItem = menu!!.findItem(R.id.app_bar_search)
        val listasItem = R.id.toolbar_listas
        val login = R.id.toolbar_login
        val mAuth = FirebaseAuth.getInstance()

        val vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        val vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)

        //Definimos el comportamiento de los items en la toolbar
        toolbar.setOnMenuItemClickListener { MenuItem ->
            when (MenuItem.itemId) {
                listasItem -> NavHostFragment.findNavController(host_fragment).navigate(R.id.listDetail)
                login -> {
                    mAuth.signOut()
                    NavHostFragment.findNavController(host_fragment).navigate(R.id.login)
                }
            }
            true
        }

        //Controlamos la busqueda dentro de la aplicación
        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //En este metodo creamos un Bundle con el titulo introducido y se lo pasamos a la clase que mostrará las peliculas que coincidan con ese titulo
                    val title = query
                    val pack = bundleOf("titulo" to title)

                    NavHostFragment.findNavController(host_fragment).navigate(R.id.Search_Movies, pack)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        newText.toLowerCase()
                    }else{
                    }
                    return true
                }
            })
        }
    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(host_fragment).navigateUp()){
            false -> moveTaskToBack(true)
        }
    }

    override fun onStart() {
        super.onStart()
        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Asignamos la action bar
        setSupportActionBar(toolbar)

        val bPopular = R.id.action_popular
        val bTop_Rated = R.id.action_top
        val bHome = R.id.action_home
        val bUpcoming = R.id.action_upcoming

        //Definimos el comportamiento de los botones de la navigation bottom bar

        bottom_navigation.setOnNavigationItemSelectedListener { MenuItem ->
            when(MenuItem.itemId){
                bHome -> NavHostFragment.findNavController(host_fragment).navigate(R.id.home_fragment)
                bPopular -> NavHostFragment.findNavController(host_fragment).navigate(R.id.popularMovies)
                bTop_Rated -> NavHostFragment.findNavController(host_fragment).navigate(R.id.topratedMovies)
                bUpcoming -> NavHostFragment.findNavController(host_fragment).navigate(R.id.upcomingMovies)
            }
            true
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken,
                    0
                )
        }
        return super.dispatchTouchEvent(ev)
    }
}
