package work.curioustools.third_party_network.interceptors

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.annotation.WorkerThread
import okhttp3.Interceptor
import work.curioustools.third_party_network.utils.InternetCheckUtils
import java.io.IOException


class InternetAvailabilityInterceptor constructor(private val ctx: Context) : Interceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @WorkerThread
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        when {
            !InternetCheckUtils.isConnectedToInternetProvider(ctx) -> {
                throw IOException("INTERNET NOT AVAILABLE")
            }
            !InternetCheckUtils.isReceivingInternetPackets() -> {
                throw IOException("INTERNET CONNECTED BUT NOT RECEIVING DATA ")
            }
            else -> {
                return chain.proceed(chain.request())
            }
        }

    }

}