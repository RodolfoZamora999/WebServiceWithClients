package com.rodolfozamora.iu.login

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.rodolfozamora.R
import com.rodolfozamora.data.model.User
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

class RegisterFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onStart() {
        super.onStart()
        val btnDiscard = requireActivity().findViewById<TextView>(R.id.txtCancelRegisterUser)
        val btnRegister = requireActivity().findViewById<Button>(R.id.btnRegisterUser)

        btnDiscard.setOnClickListener {
            activity?.onBackPressed()
        }

        btnRegister.setOnClickListener {
            val editName = requireActivity().findViewById<EditText>(R.id.editNameRegister)
            val editLastName = requireActivity().findViewById<EditText>(R.id.editLastNameRegister)
            val editEmail = requireActivity().findViewById<EditText>(R.id.editEmailRegister)
            val editPassword = requireActivity().findViewById<EditText>(R.id.editPasswordRegister)

            val name = editName.text.toString()
            val lastName = editLastName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val imageProfile = null

            val user = User(name, lastName, email,
                password, imageProfile)

            registerUser(user)
        }
    }

    private fun registerUser(user: User) {
        val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
        val serverAddress = preferences.getString(SERVER_ADDRESS, "10.0.2.2")

        val restApi = RestApiUtils(requireContext(), "https://$serverAddress:8443", null)
        restApi.registerUser(user, object : RestApiUtils.ResponseCallback<User> {
            override fun result(response: Response<User>) {
                if (response.isResponseSuccessful()) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Successful registration", Toast.LENGTH_LONG).show()
                        requireActivity().onBackPressed()
                    }
                }
                else
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
            }
        })
    }
}