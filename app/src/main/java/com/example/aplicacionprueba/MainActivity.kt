package com.example.aplicacionprueba

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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.aplicacionprueba.JsonObjets.ListMovies
import com.example.aplicacionprueba.JsonObjets.Lista
import com.example.aplicacionprueba.JsonObjets.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser




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
        val listasItem = R.id.toolbar_listas
        val login = R.id.toolbar_login

        val vari = findNavController(R.id.host_fragment)
        vari.setGraph(R.navigation.navigation_menu)

        val vari2 = AppBarConfiguration(vari.graph)

        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(vari, vari2)

        toolbar.setOnMenuItemClickListener { MenuItem ->
            when (MenuItem.itemId) {
                listasItem -> NavHostFragment.findNavController(host_fragment).navigate(R.id.misListas)

                login -> NavHostFragment.findNavController(host_fragment).navigate(R.id.login)
            }
            true

        }

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

    override fun onStart() {
        super.onStart()
        var mAuth = FirebaseAuth.getInstance()
        var currentUser = mAuth.currentUser

        if(currentUser != null){
            NavHostFragment.findNavController(host_fragment).navigate(R.id.home_fragment)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setSupportActionBar(toolbar)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

       /* myRef.setValue("Usuario1")
        myRef.child("Usuario1").setValue(Users(1, "Usuario","Usuario"))
        myRef.child("Usuario1").child("Lista1").setValue(Lista(1,1, "Lista 1"))
        myRef.child("Usuario1").child("Lista2").setValue(Lista(1,1, "Lista 2"))
        myRef.child("Usuario1").child("Lista3").setValue(Lista(1,1, "Lista 3"))
        myRef.child("Usuario1").child("Lista1").child("Movie 1").setValue(ListMovies(1,98))*/

        //val myRefLista = myRef.child(1).child()

        val bPopular = R.id.app_bar_popular
        val bTop_Rated = R.id.app_bar_toprated
        val bListas = R.id.app_bar_settings
        //val bUser = R.id.app_bar_user
        val bHome = R.id.app_bar_home

        bar.replaceMenu(R.menu.menu_bottombar_home)

        bar.setOnMenuItemClickListener { MenuItem ->
            when(MenuItem.itemId){
                bHome -> NavHostFragment.findNavController(host_fragment).navigate(R.id.home_fragment)
                bPopular -> NavHostFragment.findNavController(host_fragment).navigate(R.id.popularMovies)
                bTop_Rated -> NavHostFragment.findNavController(host_fragment).navigate(R.id.list_TopRated)
                bListas -> NavHostFragment.findNavController(host_fragment).navigate(R.id.misListas)
            }
            true
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
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
