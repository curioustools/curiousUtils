package work.curioustools.third_party_network.extensions

import retrofit2.Response

// debuggin extensions for logging
fun <T> Response<T>?.printResponse() {
    println("=====<Response>=====")
    this?.let {
        println("body = ${it.body()})")
        println("it.code = ${it.code()} ")
        println("it.isSuccessful = ${it.isSuccessful} ")
        println("msg = ${it.message()}")
        println("headers:")
        it.headers().toMultimap().forEach { (key, value) -> println("\t $key : $value") }
        println("it.errorBody = ${it.errorBody()} ")
        println("it.raw request = ${it.raw().request} ")
        it.raw().request.printRequest()
        println("it.raw response= ${it.raw()} ")
        println("it.raw response body= ${it.raw().body} ")
        println("it.raw response body msg= ${it.raw().message} ")
    } ?: println("null")
    println("=====</Response>=====")
}