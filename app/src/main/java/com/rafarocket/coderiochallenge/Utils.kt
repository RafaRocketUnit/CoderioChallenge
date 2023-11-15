package com.rafarocket.coderiochallenge

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Spanned
import androidx.appcompat.app.AlertDialog

const val baseImageUrl = "https://image.tmdb.org/t/p/original"

const val tokenAuthBearerTMDB = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTg3MDVlYWRlOGNkOGNjYzkyYmQ4NDA" +
        "wMGU0Yzk4NyIsInN1YiI6IjY1NGQzODEzZmQ0ZjgwMDBjN2ZlZmI2YyIsInNjb3BlcyI6WyJhcGlfcmVhZC" +
        "JdLCJ2ZXJzaW9uIjoxfQ.wt7Hy1kopm2deEvv6TiJ0I-OJ7_XWfZCYTs0R1_FJLM"

class Utils {

    fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun createAlertDialog(context: Context, title: String, message: Spanned) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Close") { dialog, which ->
                dialog.dismiss()
            }
        }
        builder.show()
    }
}