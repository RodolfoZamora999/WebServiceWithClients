package com.rodolfozamora.iu.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.rodolfozamora.R
import com.rodolfozamora.iu.contact.ContactActivity

class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        requireActivity().findViewById<Button>(R.id.btnLogin).apply {
            setOnClickListener {
                //Validate email and password
                startActivity(Intent(context, ContactActivity::class.java))
            }
        }

        requireActivity().findViewById<TextView>(R.id.txtRegisterLogin).apply {
            setOnClickListener {
                Navigation.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}