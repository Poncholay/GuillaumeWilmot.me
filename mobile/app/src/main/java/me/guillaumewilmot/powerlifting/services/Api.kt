package me.guillaumewilmot.powerlifting.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.guillaumewilmot.powerlifting.R
import me.guillaumewilmot.powerlifting.models.requests.GsonRequest
import me.guillaumewilmot.powerlifting.models.responses.ApiErrorResponse
import me.guillaumewilmot.powerlifting.models.responses.ApiResponse
import me.guillaumewilmot.powerlifting.models.responses.SigninResponse
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

fun VolleyError.errMsg(context: Context? = null, fallback: String = ""): String {
    try {
        if (context != null) {
            this.cause?.printStackTrace()
            when (this.cause) {
                is UnknownHostException -> return context.getString(R.string.internet_error)
                is ConnectException -> return context.getString(R.string.internet_error)
                is SocketException -> return context.getString(R.string.internet_error)
            }
        }
        networkResponse?.let {
            return Gson().fromJson(String(networkResponse.data), ApiErrorResponse::class.java).error.capitalize()
        }
        message?.let { error ->
            return error.capitalize()
        }
    } catch (e: Exception) {
    }
    return fallback.capitalize()
}

object Api {
    //    private const val host = "http://api.guillaumewilmot.me"
    private const val host = "http://localhost:9050"

    inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

    object Auth {
        private const val googleSignin = "/auth/login/google"

        /**
         * data:
         * email: String
         * password: String
         */
        fun googleSignin(
            data: Any,
            callback: Response.Listener<ApiResponse<SigninResponse>>,
            error: Response.ErrorListener
        ):
                GsonRequest<ApiResponse<SigninResponse>> =
            GsonRequest(
                Request.Method.POST,
                host + googleSignin,
                data,
                genericType<ApiResponse<SigninResponse>>(),
                null,
                callback,
                error
            )
    }
}