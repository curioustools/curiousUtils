package work.curioustools.third_party_network.interceptors

import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {
    fun getInstance(severity: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().run {
            level = severity
            this
        }
    }
}