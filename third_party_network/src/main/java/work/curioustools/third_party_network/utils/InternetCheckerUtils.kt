package work.curioustools.third_party_network.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import work.curioustools.third_party_network.interceptors.InternetAvailabilityInterceptor

object InternetCheckerUtils{
    fun getCheckerInterceptor(ctx: Context): InternetAvailabilityInterceptor {
        return InternetAvailabilityInterceptor(ctx)
    }
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(ctx: Context): Boolean {
        val cm = ctx.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        cm?:return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

        } else {
            cm.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}