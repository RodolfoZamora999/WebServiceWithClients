package com.rodolfozamora.network.api

const val RESPONSE_STATUS_SUCCESS = 1;
const val RESPONSE_STATUS_FAIL = -1;

class Response<T> (private val status: Int, var data: T?, var message: String?) {

    fun isResponseSuccessful(): Boolean {
        return status == RESPONSE_STATUS_SUCCESS
    }

    fun hasData(): Boolean {
        return data != null
    }

    fun hasMessage(): Boolean {
        return !message.isNullOrEmpty()
    }
}