package work.curioustools.third_party_network.extensions

import android.util.Log
import retrofit2.Response


fun <T> Response<T>?.logRetrofitResponse() {
    val TAG = "RETROFIT>>"
    this?.let {
        Log.e(TAG, "body = ${it.body()})")
        Log.e(TAG, "it.code = ${it.code()} ")
        Log.e(TAG, "it.isSuccessful = ${it.isSuccessful} ")
        Log.e(TAG, "msg = ${it.message()}")
        Log.e(TAG, "headers:")
        it.headers().toMultimap().forEach { (key, value) -> Log.e(TAG, "\t $key : $value") }
        Log.e(TAG, "it.errorBody = ${it.errorBody()} ")
        Log.e(TAG, "it.raw request = ${it.raw().request} ")
        Log.e(TAG, "it.raw request body= ${it.raw().request.body} ")
        Log.e(TAG, "it.raw request headers= ${it.raw().request.headers} ")
        Log.e(TAG, "it.raw response= ${it.raw()} ")

        Log.e(TAG, "it.raw response body= ${it.raw().body} ")
        Log.e(TAG, "it.raw response body msg= ${it.raw().message} ")

        Log.e(TAG, "===========================================")
    }
}

//inline fun <reified T> Retrofit.createApi(): T = this.create(T::class.java)
//fun Retrofit.updateBaseUrl(baseUrl: String): Retrofit = this.newBuilder().baseUrl(baseUrl).build()
