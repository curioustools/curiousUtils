package work.curioustools.third_party_network.extensions

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.annotation.WorkerThread
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Response
import work.curioustools.third_party_network.arch_network.BaseStatus
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class HeaderInterceptor(
    private val appHeaders: HashMap<String, String>,
    private val skipAllHeaderEnabled: Boolean = false,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return synchronized(this) {
            val originalRequest = chain.request()
            val requestBuilder =
                if (originalRequest.header(HEADER_SKIP_ALL).contentEquals(HEADER_SKIP_ALL) && skipAllHeaderEnabled) {
                    originalRequest.newBuilder().removeHeader(HEADER_SKIP_ALL)
                }
                else {
                    originalRequest.newBuilder().apply {
                        appHeaders.forEach { (key, value) ->
                            header(key, value)
                        }
                    }
                }

            chain.proceed(requestBuilder.build())
        }
    }

    companion object {
        val OS_PAIR = Pair("x-os", "android")
        val CONTENT_TYPE_PAIR = Pair("Content-Type", "application/json")
        val API_AUTH_PAIR = Pair("app-id", "abcd")//todo use gradle properties/gradle keystore/gradle keychain for this
        const val HEADER_SKIP_ALL = "x-skip-all"
        const val HEADER_AUTH = "Authorization"
        const val HEADER_APP_VERSION = "x-app-version"
        const val ACCESS_COOKIE = "x-access-cookie"
        const val CACHE_CONTROL = "cache-control"
        const val CACHE_CONTROL_NO_CACHE = "no-cache"
        const val HEADER_TIME_ZONE = "x-timezone-offset"
        const val HEADER_USER_KIND = "x-user-kind"
        const val HEADER_APPLICATION_NAME = "x-application-name"
        const val APPLICATION_NAME = "APP_NAME"
    }

}

// a handy interceptor which will check for both internet connectivity and availability
class InternetCheckInterceptor(private val context: Context? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        // will not work and simply allow the request to proceed if context is null
        if (context == null) return chain.proceed(chain.request())

        val resp = when {
            !isConnectedToInternetProvider(context) -> throw Exception(BaseStatus.NO_INTERNET_CONNECTION.msg)
            !isReceivingInternetPackets() -> throw IOException(BaseStatus.NO_INTERNET_PACKETS_RECEIVED.msg)
            else -> chain.proceed(chain.request())
        }
        //todo saving context might cause leak? todo check via leakcanary
        return resp
    }

    companion object {
        @JvmStatic
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnectedToInternetProvider(ctx: Context): Boolean {
            val cm = ctx.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            cm ?: return false
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {

                    val currentNetwork = cm.activeNetwork ?: return false
                    val capabilities = cm.getNetworkCapabilities(currentNetwork) ?: return false
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
                else -> {
                    val currentNetworkINFO = cm.activeNetworkInfo ?: return false
                    currentNetworkINFO.isConnectedOrConnecting
                            || currentNetworkINFO.type == ConnectivityManager.TYPE_WIFI
                            || currentNetworkINFO.type == ConnectivityManager.TYPE_MOBILE
                }
            }
        }

        @JvmStatic
        @WorkerThread
        fun isReceivingInternetPackets(): Boolean {
            val dnsPort = 53
            val googleIp = "8.8.8.8"
            val timeOut = 1500
            return kotlin.runCatching {
                val socket = Socket()
                val inetAddress = InetSocketAddress(googleIp, dnsPort)
                socket.connect(inetAddress, timeOut)
                socket.close()
                true
            }.getOrDefault(false)
        }
    }
}

fun OkHttpClient.Builder.addHeaderInterceptor(appHeaders: HashMap<String, String>, skipAllHeaderEnabled: Boolean): OkHttpClient.Builder {
    addInterceptor(HeaderInterceptor(appHeaders, skipAllHeaderEnabled))
    return this
}

fun OkHttpClient.Builder.addInternetCheckInterceptor(context: Context): OkHttpClient.Builder {
    addInterceptor(InternetCheckInterceptor(context))
    return this
}

fun OkHttpClient.Builder.addLoggingInterceptor(severity: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): OkHttpClient.Builder {
    addInterceptor(HttpLoggingInterceptor().also { it.level = severity })
    return this
}