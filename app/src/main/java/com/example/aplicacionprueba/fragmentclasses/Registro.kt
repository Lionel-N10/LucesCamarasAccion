package com.example.aplicacionprueba.fragmentclasses

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
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.aplicacionprueba.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Registro.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Registro.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Registro : Fragment() {
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mAuth = FirebaseAuth.getInstance()
        val textEmail: EditText = view.findViewById(R.id.signup_name)
        val textPass: EditText = view.findViewById(R.id.signup_pass)
        val bRegistrarme: Button = view.findViewById(R.id.signup_button)

        bRegistrarme.setOnClickListener {
            try {
                val email = textEmail.text.toString()
                val pass = textPass.text.toString()
                mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this.activity!!, OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            Navigation.findNavController(view).navigate(RegistroDirections.actionRegistroToLogin())
                            Log.d(TAG, "createUserWithEmail:success")
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(context!!, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    })
                /*val t = Thread {
                        val user: Users? = Users(0, usuario.text.toString(), pass.text.toString())
                        DataBase(context!!).DaoUser().insertUser(user!!)
                    }
                    t.start()
                    t.join()
                    Toast.makeText(context!!, "Usuario creado", Toast.LENGTH_SHORT).show()*/
            }catch (iae : IllegalArgumentException){
                Log.d("ERROR" ,iae.message)
                Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
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
         * @return A new instance of fragment Registro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Registro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
