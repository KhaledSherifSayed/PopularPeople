package com.meslmawy.ibtkarchallenge

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import android.widget.TextView.BufferType


const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1200

sealed class State<T> {
    class Loading<T> : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val message: String) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()

        fun <T> success(data: T) = Success(data)


        fun <T> error(message: String) = Error<T>(message)
    }
}


fun isNetworkAvailable(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

fun image_path(string: String) : String {
    return "https://image.tmdb.org/t/p/w500/$string"
}