package com.rodolfozamora.network.api

import android.content.Context
import com.google.gson.Gson
import com.rodolfozamora.data.model.AuthUser
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.data.model.User
import com.rodolfozamora.network.createOkHttpSecureBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

class RestApiUtils(context: Context, private val baseUrl: String, private val jwtToken: String?) {
    private var httpClient = createOkHttpSecureBuilder(context).build()

    fun registerUser(user: User, callBack: ResponseCallback<User>) {
        val url = this.baseUrl.plus("/api/users")
        val dataJson = Gson().toJson(user)
        val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = dataJson.toRequestBody(jsonMimeType)
        val request = Request.Builder().url(url).post(body).build()

        var resp: Response<User>
        this.httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resp = Response(RESPONSE_STATUS_FAIL, null, e.message)
                callBack.result(resp)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.code in 200..210) {
                    resp = Response(RESPONSE_STATUS_SUCCESS, null, "Successful response")
                    callBack.result(resp)
                }
                else {
                    resp = Response(RESPONSE_STATUS_FAIL, null, response.body!!.string())
                    callBack.result(resp)
                }
            }
        })
    }

    fun registerContact(idUser: Int, contact: Contact, callBack: ResponseCallback<Contact>) {
        val url = this.baseUrl.plus("/api/users/$idUser/contacts")
        val dataJson = Gson().toJson(contact)
        val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = dataJson.toRequestBody(jsonMimeType)
        val request = Request.Builder().url(url).post(body).
                            addHeader("Authorization", "Bearer $jwtToken").build()

        var resp: Response<Contact>
        this.httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resp = Response(RESPONSE_STATUS_FAIL, null, e.message)
                callBack.result(resp)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.code in 200..210) {
                    resp = Response(RESPONSE_STATUS_SUCCESS, null, "Successful response")
                    callBack.result(resp)
                }
                else {
                    resp = Response(RESPONSE_STATUS_FAIL, null, response.body!!.string())
                    callBack.result(resp)
                }
            }
        })
    }

    fun getAllContacts(idUser: Int, callBack: ResponseCallback<Array<Contact>>) {
        val url = this.baseUrl.plus("/api/users/$idUser/contacts")
        val request = Request.Builder().url(url).get().addHeader("Authorization", "Bearer $jwtToken").build()

        var resp: Response<Array<Contact>>
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resp = Response(RESPONSE_STATUS_FAIL, null, e.message)
                callBack.result(resp)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.code in 200..210) {
                    val jsonResponse = response.body!!.string()
                    val gson = Gson()
                    val contacts = gson.fromJson(jsonResponse, Array<Contact>::class.java)
                    resp = Response(RESPONSE_STATUS_SUCCESS, contacts, "Successful response")
                    callBack.result(resp)
                }
                else {
                    resp = Response(RESPONSE_STATUS_FAIL, null, response.body!!.string())
                    callBack.result(resp)
                }
            }
        })
    }

    fun uploadImage(file: File, callBack: ResponseCallback<String>) {
        val url = this.baseUrl.plus("/api/images")
        val mediaType = "image/png".toMediaType()
        val requestBody = file.asRequestBody(mediaType)
        val request = Request.Builder().url(url).post(requestBody).build();

        var resp : Response<String>
        this.httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resp = Response(RESPONSE_STATUS_FAIL, null, e.message)
                callBack.result(resp)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.code in 200..210) {
                    resp = Response(RESPONSE_STATUS_SUCCESS, response.body!!.string(), "Successful response")
                    callBack.result(resp)
                }
                else {
                    resp = Response(RESPONSE_STATUS_FAIL, null, response.body!!.string())
                    callBack.result(resp)
                }
            }
        })
    }

    fun login(authUser: AuthUser, callBack: ResponseCallback<String>) {
        val url = baseUrl.plus("/api/login")
        val dataJson = Gson().toJson(authUser)
        val jsonMimeType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = dataJson.toRequestBody(jsonMimeType)
        val request = Request.Builder().url(url).post(body).build()

        var resp: Response<String>
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                resp = Response(RESPONSE_STATUS_FAIL, null, e.message)
                callBack.result(resp)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                if (response.code in 200..210) {
                    resp = Response(RESPONSE_STATUS_SUCCESS, response.body!!.string(), "Successful response")
                    callBack.result(resp)
                }
                else {
                    resp = Response(RESPONSE_STATUS_FAIL, null, response.body!!.string())
                    callBack.result(resp)
                }
            }
        })
    }

    @FunctionalInterface
    interface ResponseCallback<T> {
        fun result(response: Response<T>)
    }
}