package work.curioustools.third_party_network.extensions

import android.annotation.SuppressLint
import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.*
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.Proxy
import java.net.ProxySelector
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.*

val cookieJar = JavaNetCookieJar(CookieManager().also { it.setCookiePolicy(CookiePolicy.ACCEPT_ALL) })
fun getSocketFactoryAndUnsafeTrustManager(): Pair<SSLSocketFactory, X509TrustManager> {
    val trustAllCerts = // a trust manager that does not validate certificate chains
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }

    // Install the all-trusting trust manager and  Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory: SSLSocketFactory = SSLContext.getInstance("SSL").let {
        it.init(null, arrayOf(trustAllCerts), SecureRandom())
        it.socketFactory
    }
    return Pair(sslSocketFactory, trustAllCerts)
}

fun getOkHttpClientBuilder(
    authenticator: Authenticator? = null,
    cache: Cache? = null,
    callTimeout: Pair<Long, TimeUnit>? = null,
    certificatePinner: CertificatePinner? = null,
    connectionPool: ConnectionPool? = null,
    connectionSpecList: List<ConnectionSpec>? = null,
    connectTimeout: Pair<Long, TimeUnit>? = null,
    cookieJar: CookieJar? = null,
    dispatcher: Dispatcher? = null,
    dns: Dns? = null,
    elFactory: EventListener.Factory? = null,
    eventListener: EventListener? = null,
    followRedirects: Boolean? = null,
    followSSLRedirects: Boolean? = null,
    hostnameVerifier: HostnameVerifier? = null,
    interceptors: List<Interceptor> = listOf(),
    minCompress: Long? = null,
    networkInterceptors: List<Interceptor> = listOf(),
    pingInterval: Pair<Long, TimeUnit>? = null,
    protocols: List<Protocol>? = null,
    proxy: Proxy? = null,
    proxyAuthenticator: Authenticator? = null,
    proxySelector: ProxySelector? = null,
    readTimeout: Pair<Long, TimeUnit>? = null,
    retryOnConnectionFailure: Boolean? = null,
    socketFactory: SocketFactory? = null,
    sslSocketFactory: Pair<SSLSocketFactory, X509TrustManager>? = null,
    writeTimeout: Pair<Long, TimeUnit>? = null, ): OkHttpClient.Builder {
    return OkHttpClient.Builder().let { builder ->
        authenticator?.let { builder.authenticator(it) }
        cache?.let { builder.cache(cache) }
        callTimeout?.let { builder.callTimeout(it.first, it.second) }
        certificatePinner?.let { builder.certificatePinner(it) }
        connectionPool?.let { builder.connectionPool(it) }
        connectionSpecList?.let { builder.connectionSpecs(it) }
        connectTimeout?.let { builder.connectTimeout(it.first, it.second) }
        cookieJar?.let { builder.cookieJar(it) }
        dispatcher?.let { builder.dispatcher(it) }
        dns?.let { builder.dns(it) }
        elFactory?.let { builder.eventListenerFactory(it) }
        eventListener?.let { builder.eventListener(it) }
        followRedirects?.let { builder.followRedirects(it) }
        followSSLRedirects?.let { builder.followSslRedirects(it) }
        hostnameVerifier?.let { builder.hostnameVerifier(it) }
        interceptors.forEach { builder.addInterceptor(it) }
        minCompress?.let { builder.minWebSocketMessageToCompress(it) }
        networkInterceptors.forEach { builder.addNetworkInterceptor(it) }
        pingInterval?.let { builder.pingInterval(it.first, it.second) }
        pingInterval?.let { builder.pingInterval(it.first, it.second) }
        protocols?.let { builder.protocols(it) }
        proxy?.let { builder.proxy(it) }
        proxyAuthenticator?.let { builder.proxyAuthenticator(it) }
        proxySelector?.let { builder.proxySelector(it) }
        readTimeout?.let { builder.readTimeout(it.first, it.second) }
        retryOnConnectionFailure?.let { builder.retryOnConnectionFailure(it) }
        socketFactory?.let { builder.socketFactory(it) }
        sslSocketFactory?.let { builder.sslSocketFactory(it.first, it.second) }
        writeTimeout?.let { builder.writeTimeout(it.first, it.second) }
        builder
    }
}

fun getMyOkHttpBuilderBasic(
    sslSocketFactory: Pair<SSLSocketFactory, X509TrustManager>? = null,
    cookieJar: CookieJar? = null,
    retryOnConnectionFailure: Boolean? = null
) =  getOkHttpClientBuilder(
    readTimeout = Pair(1L, TimeUnit.MINUTES),
    writeTimeout = Pair(1L, TimeUnit.MINUTES),
    connectTimeout = Pair(1L, TimeUnit.MINUTES),
    sslSocketFactory = sslSocketFactory,
    cookieJar = cookieJar,
    retryOnConnectionFailure = retryOnConnectionFailure
)
fun getMyUnsafeOkHttpBuilderPROD(appHeaders: HashMap<String, String>?, skipAllHeaderEnabled: Boolean?, ctx:Context?): OkHttpClient.Builder {
    val builder =  getMyOkHttpBuilderBasic(getSocketFactoryAndUnsafeTrustManager(), cookieJar,true)

    if(appHeaders!=null && skipAllHeaderEnabled!=null) builder.addHeaderInterceptor(appHeaders, skipAllHeaderEnabled)
    if(ctx!=null) builder.addInternetCheckInterceptor(ctx)

    return builder
}

fun getMyUnsafeOkHttpBuilderDEBUG(appHeaders: HashMap<String, String>?, skipAllHeaderEnabled: Boolean?,ctx: Context?): OkHttpClient.Builder {
    val builder = getMyUnsafeOkHttpBuilderPROD(appHeaders, skipAllHeaderEnabled,ctx)
    builder.addNetworkInterceptor(StethoInterceptor())
    builder.addLoggingInterceptor()

    return builder


}
