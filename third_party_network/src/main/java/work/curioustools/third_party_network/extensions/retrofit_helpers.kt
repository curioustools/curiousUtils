package work.curioustools.third_party_network.extensions

import okhttp3.OkHttpClient
import retrofit2.*
import java.util.concurrent.Executor

fun getRetrofitBuilder(
    baseUrl: String,
    client: OkHttpClient,
    convertorFactories: List<Converter.Factory> = listOf(),
    callAdapterFactories: List<CallAdapter.Factory> = listOf(),
    callbackExecutor: Executor? = null,
    callFactory: okhttp3.Call.Factory? = null,
    validateEagerly: Boolean? = null, ): Retrofit.Builder {
    return Retrofit.Builder().also { builder ->
        convertorFactories.forEach { builder.addConverterFactory(it) }
        callAdapterFactories.forEach { builder.addCallAdapterFactory(it) }
        baseUrl.let { builder.baseUrl(baseUrl) }
        client.let { builder.client(it) }
        callFactory?.let { builder.callFactory(it) }
        callbackExecutor?.let { builder.callbackExecutor(it) }
        validateEagerly?.let { builder.validateEagerly(it) }
    }
}