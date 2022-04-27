package com.rodolfozamora.network.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.rodolfozamora.data.model.AuthUser
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.data.model.User
import com.rodolfozamora.network.createOkHttpSecureBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.lang.Exception

class RestApiUtils(context: Context, private val baseUrl: String, private val jwtToken: String?) {
    private var httpClient = createOkHttpSecureBuilder(context).build()

    fun registerUser(user: User, callBack: ResponseCallback<User>) {
        Thread {
            var response: Response<User>
            try {
                val finalUrl = baseUrl.plus("/api/users")
                val dataJson = Gson().toJson(user)
                val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = dataJson.toRequestBody(jsonMimeType)

                val request = Request.Builder().url(finalUrl).post(body).build()
                val httpResponse = httpClient.newCall(request).execute()

                response = if (httpResponse.code in 200..210)
                    Response(RESPONSE_STATUS_SUCCESS, null, "Successful response")
                else
                    Response(RESPONSE_STATUS_FAIL, null, httpResponse.body?.string())

            } catch (ex: Exception) {
                response = Response(RESPONSE_STATUS_FAIL, null, ex.message)
            }
            callBack.result(response)
        }.start()
    }

    fun registerContact(idUser: Int, contact: Contact, callBack: ResponseCallback<Contact>) {
        Thread {
            var response: Response<Contact>
            try {
                val finalUrl = baseUrl.plus("/api/users/$idUser/contacts")
                val dataJson = Gson().toJson(contact)
                val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = dataJson.toRequestBody(jsonMimeType)

                val request = Request.Builder().url(finalUrl).post(body)
                    .addHeader("Authorization", "Bearer $jwtToken").build()
                val httpResponse = httpClient.newCall(request).execute()

                Log.d("CONTACT", request.toString())
                Log.d("CONTACT", httpResponse.toString())

                response = if (httpResponse.code in 200..210)
                    Response(RESPONSE_STATUS_SUCCESS, null, "Successful response")
                else
                    Response(RESPONSE_STATUS_FAIL, null, httpResponse.body?.string())

            } catch (ex: Exception) {
                response = Response(RESPONSE_STATUS_FAIL, null, ex.message)
            }
            callBack.result(response)
        }.start()
    }

    fun getAllContacts(idUser: Int, callBack: ResponseCallback<Array<Contact>>) {
        Thread {
            var response: Response<Array<Contact>>
            try {
                val finalUrl = baseUrl.plus("/api/users/$idUser/contacts")
                //val dataJson = Gson().toJson(contact)
                //val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
                //val body = dataJson.toRequestBody(jsonMimeType)

                Log.d("CONTACT", "Before")
                val request = Request.Builder().url(finalUrl).get()
                    .addHeader("Authorization", "Bearer $jwtToken").build()
                val httpResponse = httpClient.newCall(request).execute()

                Log.d("CONTACT", request.toString())
                Log.d("CONTACT", httpResponse.toString())

                //Converts json response to Array contacts
                val jsonResponse = httpResponse.body!!.string()
                val gson = Gson()
                val contacts = gson.fromJson(jsonResponse, Array<Contact>::class.java)

                response = if (httpResponse.code in 200..210)
                    Response(RESPONSE_STATUS_SUCCESS, contacts, "Successful response")
                else
                    Response(RESPONSE_STATUS_FAIL, null, httpResponse.body?.string())

            } catch (ex: Exception) {
                ex.printStackTrace()
                response = Response(RESPONSE_STATUS_FAIL, null, ex.message)
            }
            callBack.result(response)
        }.start()


    }

    fun uploadImage(input: InputStream, callBack: ResponseCallback<String>) {

    }

    fun login(authUser: AuthUser, callBack: ResponseCallback<String>) {
        var response: Response<String>
        Thread {
            try {
                val finalUrl = baseUrl.plus("/api/login")
                val dataJson = Gson().toJson(authUser)
                val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val body = dataJson.toRequestBody(jsonMimeType)

                val request = Request.Builder().url(finalUrl).post(body).build()
                val httpResponse = httpClient.newCall(request).execute()

                response = if (httpResponse.code in 200..210)
                    Response(
                        RESPONSE_STATUS_SUCCESS,
                        httpResponse.body?.string(),
                        "Successful response"
                    )
                else
                    Response(RESPONSE_STATUS_FAIL, null, httpResponse.body?.string())

            } catch (ex: Exception) {
                response = Response(RESPONSE_STATUS_FAIL, null, ex.message)
            }
            callBack.result(response)
        }.start()
    }

    @FunctionalInterface
    interface ResponseCallback<T> {
        fun result(response: Response<T>)
    }
}