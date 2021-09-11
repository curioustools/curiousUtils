package work.curioustools.third_party_network.utils

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import work.curioustools.third_party_network.interceptors.HeaderInterceptor
import work.curioustools.third_party_network.interceptors.InternetAvailabilityInterceptor
import work.curioustools.third_party_network.interceptors.OkHttpLoggingInterceptorUtils
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object OkHttpUtils {

    fun getSocketFactoryAndUnsafeTrustManager(): Pair<SSLSocketFactory, X509TrustManager> {
        val sslContext: SSLContext = SSLContext.getInstance("SSL") // Install the all-trusting trust manager

        val trustAllCerts: Array<TrustManager> = arrayOf(// Create a trust manager that does not validate certificate chains
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) = Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) = Unit

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory // Create an ssl socket factory with our all-trusting manager
        val tm: X509TrustManager = trustAllCerts[0] as X509TrustManager

        return Pair(sslSocketFactory, tm)
    }

    fun getClient(
        connectTimeout: Pair<Long, TimeUnit> = Pair(1L, TimeUnit.MINUTES),
        writeTimeout: Pair<Long, TimeUnit> = Pair(1L, TimeUnit.MINUTES),
        readTimeout: Pair<Long, TimeUnit> = Pair(1L, TimeUnit.MINUTES),
        retryOnConnectionFailure: Boolean = true,
        headerInterceptor: HeaderInterceptor? = null,
        loggingInterceptor: HttpLoggingInterceptor? = null,
        internetCheckInterceptor: InternetAvailabilityInterceptor? = null,
        otherInterceptors: List<Interceptor> = listOf(),
        stetho: Interceptor? = null,
        otherNetworkInterceptors:List<Interceptor> = listOf(),
        socketFactory: Pair<SSLSocketFactory, X509TrustManager>? = null

    ): OkHttpClient {
        return OkHttpClient.Builder().let { builder ->
            builder.connectTimeout(connectTimeout.first, connectTimeout.second)
            builder.writeTimeout(writeTimeout.first, writeTimeout.second)
            builder.readTimeout(readTimeout.first, readTimeout.second)
            builder.retryOnConnectionFailure(retryOnConnectionFailure)
            headerInterceptor?.let { builder.addInterceptor(it) }
            loggingInterceptor?.let { builder.addInterceptor(it) }
            internetCheckInterceptor?.let { builder.addInterceptor(it) }
            otherInterceptors.forEach { builder.addInterceptor(it) }

            stetho?.let { builder.addNetworkInterceptor(it) }
            otherNetworkInterceptors.forEach { builder.addNetworkInterceptor(it) }

            socketFactory?.let { pair -> kotlin.runCatching { builder.sslSocketFactory(pair.first, pair.second) } }

            builder.build()
        }
    }


    fun getClient(headers: HashMap<String, String> ):OkHttpClient{
        return getClient(headerInterceptor = HeaderInterceptor(headers))
    }

    fun getDebugClient():OkHttpClient{
        return getClient(
            loggingInterceptor = OkHttpLoggingInterceptorUtils.getInterceptor(),
            stetho = StethoInterceptor()
        )
    }

}