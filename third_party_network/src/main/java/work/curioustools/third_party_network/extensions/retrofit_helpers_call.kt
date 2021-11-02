package work.curioustools.third_party_network.extensions

import retrofit2.Call

fun <T> Call<T>.printCurrentCall(msg:String =""){
    val TAG = "RETROFIT>>"
    println( "logCurrentCall() called with: msg = $msg")
    request().let {
        println( "logCurrentCall: body :  ${it.body}")
        println( "logCurrentCall: cacheControl ${it.cacheControl}")
        it.headers.toMultimap().forEach { (key, value) -> println( "\t $key : $value") }
        println( "logCurrentCall: ishttps ${it.isHttps}")
        println( "logCurrentCall: method ${it.method}")
        println( "logCurrentCall: url ${it.url}")
    }

}