package work.curioustools.third_party_network.interceptors

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import okhttp3.Interceptor
import work.curioustools.third_party_network.utils.InternetCheckerUtils
import java.io.IOException


class InternetAvailabilityInterceptor constructor(private val ctx: Context) : Interceptor {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        if (!InternetCheckerUtils.isNetworkAvailable(ctx)) {
            throw IOException("INTERNET NOT AVAILABLE")
        } else {
            return chain.proceed(chain.request())
        }

    }


    companion object{
    }
}