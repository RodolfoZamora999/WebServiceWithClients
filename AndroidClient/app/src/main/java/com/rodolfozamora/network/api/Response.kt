package com.rodolfozamora.network.api

const val RESPONSE_STATUS_SUCCESS = 1;
const val RESPONSE_STATUS_FAIL = -1;

class Response<T> (private val status: Int, var data: T?) {
    fun isResponseSuccessful(): Boolean {
        return status == RESPONSE_STATUS_SUCCESS
    }
}