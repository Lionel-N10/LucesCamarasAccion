package com.example.aplicacionprueba

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Login.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val bottomBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)
        bottomBar.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomBar: BottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)
        bottomBar.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar!!.hide()

        val emailText = view.findViewById<EditText>(R.id.login_user)
        val passText = view.findViewById<EditText>(R.id.login_pass)
        val textusuario: EditText = view.findViewById(R.id.login_user)
        val textpass: EditText = view.findViewById(R.id.login_pass)
        val Blogin: Button = view.findViewById(R.id.bLogIn)
        val Bsingup: Button = view.findViewById(R.id.bSingUp)

        val mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null){
            NavHostFragment.findNavController(host_fragment).navigate(LoginDirections.actionLoginToHomeFragment())
        }

        /*val email = Textusuario.text
        val pass = Textpass.text*/

        Blogin.setOnClickListener {
            try {

                mAuth.signInWithEmailAndPassword(emailText.text.toString(), passText.text.toString())
                    .addOnCompleteListener(this.activity!!, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            NavHostFragment.findNavController(host_fragment).navigate(LoginDirections.actionLoginToHomeFragment())
                            Log.d(TAG, "signInWithEmail:success")
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context!!, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }catch (iae : java.lang.IllegalArgumentException){
                Log.d("Error", iae.message.toString())

                Toast.makeText(context!!, "Ningún campo debe estar vacio", Toast.LENGTH_SHORT).show()
            }

        }
        Bsingup.setOnClickListener {
            NavHostFragment.findNavController(host_fragment).navigate(LoginDirections.actionLoginToRegistro())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
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
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
