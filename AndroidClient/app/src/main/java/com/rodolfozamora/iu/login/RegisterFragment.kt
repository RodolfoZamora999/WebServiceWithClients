package com.rodolfozamora.iu.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.rodolfozamora.R
import com.rodolfozamora.data.model.User
import com.rodolfozamora.network.createOkHttpSecureBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

class RegisterFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onStart() {
        super.onStart()
        val btnDiscard = activity?.findViewById<Button>(R.id.btnCancelRegister)
        val btnRegister = activity?.findViewById<Button>(R.id.btnRegisterRegister)

        btnDiscard?.setOnClickListener {
            Toast.makeText(context, "Cancel...", Toast.LENGTH_LONG).show()
            activity?.onBackPressed()
        }

        btnRegister?.setOnClickListener {
            val editName = activity?.findViewById<EditText>(R.id.editNameRegister)
            val editLastName = activity?.findViewById<EditText>(R.id.editLastNameRegister)
            val editEmail = activity?.findViewById<EditText>(R.id.editEmailRegister)
            val editPassword = activity?.findViewById<EditText>(R.id.editPasswordRegister)

            val name = editName?.text.toString()
            val lastName = editLastName?.text.toString()
            val email = editEmail?.text.toString()
            val password = editPassword?.text.toString()
            val imageProfile = null

            val user = User(name, lastName, email,
                password, imageProfile)

            registerUser(user)
        }
    }

    private fun registerUser(user: User) {
        val baseUrl = "https://10.0.2.2:8443"
        val apiUrl = "/api/users"
        val finalUrl = baseUrl.plus(apiUrl)
        val dataJson = Gson().toJson(user)

        val httpClient = createOkHttpSecureBuilder(requireActivity().baseContext).build()
        val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = dataJson.toRequestBody(jsonMimeType)
        val request = Request.Builder().url(finalUrl).post(body).build()

        Thread {
            try {
                val response = httpClient.newCall(request).execute()
                val statusCode = response.code
                if (statusCode in 200..210) {
                    Log.d("RESPONSE", "Response: " + response.body?.string())
                }
                else {
                    Log.d("RESPONSE", "Fail: " + response.body?.string())
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()
    }
}