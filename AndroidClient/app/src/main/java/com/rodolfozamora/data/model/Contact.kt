package com.rodolfozamora.data.model

import java.io.Serializable

data class Contact(var id: Int?, val name: String, val lastName: String, val phoneNumber: String,
                    val email: String?, val imageProfile: String?) : Serializable
