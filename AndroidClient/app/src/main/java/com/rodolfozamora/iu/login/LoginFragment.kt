package com.rodolfozamora.iu.login

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.rodolfozamora.R
import com.rodolfozamora.data.model.AuthUser
import com.rodolfozamora.iu.config.ConfigActivity
import com.rodolfozamora.iu.contact.ContactActivity
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.persistence.CURRENT_USER
import com.rodolfozamora.persistence.JWT_TOKEN
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        val btnLogin = requireActivity().findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            //Validate email and password
            val editEmail = requireActivity().findViewById<EditText>(R.id.editEmailLogin)
            val editPassword = requireActivity().findViewById<EditText>(R.id.editPasswordLogin)

            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val authUser = AuthUser(email, password)

            loginAccount(authUser)
        }

        val txtRegister = requireActivity().findViewById<TextView>(R.id.txtRegisterLogin)
        txtRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val btnSettings = requireActivity().findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), ConfigActivity::class.java))
        }
    }

    private fun loginAccount(authUser: AuthUser) {
        val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
        val serverAddress = preferences.getString(SERVER_ADDRESS, "10.0.2.2")

        val restApi = RestApiUtils(requireContext(), "https://$serverAddress:8443", null)
        restApi.login(authUser, object: RestApiUtils.ResponseCallback<String> {
            override fun result(response: Response<String>) {
                if (response.isResponseSuccessful() && response.hasData()) {
                    val dataJson = JsonParser.parseString(response.data).asJsonObject
                    val jwtToken = dataJson.get("token").asString
                    val user = dataJson.get("user").asString

                    val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
                    val status = preferences.edit().putString(CURRENT_USER, user).
                                            putString(JWT_TOKEN, jwtToken).commit()

                    requireActivity().runOnUiThread {
                        startActivity(Intent(context, ContactActivity::class.java))
                    }
                }
                else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}