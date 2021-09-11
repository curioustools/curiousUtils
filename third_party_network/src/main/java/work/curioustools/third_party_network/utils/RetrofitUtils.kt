package work.curioustools.third_party_network.utils

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object RetrofitUtils {
    fun getRetrofit(
        baseUrl: String = "",
        okHttpClient: OkHttpClient = OkHttpUtils.getDebugClient(),
        externalConvertors: MutableList<Converter.Factory?> = mutableListOf(
            ScalarConvertorUtils.getConvertorOrNull(),
            MoshiUtils.getMoshiConvertorOrNull()
        ),
        callAdapterFactories: List<CallAdapter.Factory> = listOf(),
        executor: Executor? = Executors.newSingleThreadExecutor()
    ): Retrofit {
        return Retrofit.Builder().let { builder ->
            builder.baseUrl(baseUrl)
            builder.client(okHttpClient)
            externalConvertors.filterNotNull().forEach { builder.addConverterFactory(it) }
            callAdapterFactories.forEach { builder.addCallAdapterFactory(it) }
            executor?.let { builder.callbackExecutor(it) }
            builder.build()
        }
    }

}