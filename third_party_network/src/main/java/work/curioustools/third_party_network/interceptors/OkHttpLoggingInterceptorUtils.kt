package work.curioustools.third_party_network.interceptors

import okhttp3.logging.HttpLoggingInterceptor

object OkHttpLoggingInterceptorUtils {
    fun getInterceptor(severity: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().run {
            level = severity
            this
        }
    }
}