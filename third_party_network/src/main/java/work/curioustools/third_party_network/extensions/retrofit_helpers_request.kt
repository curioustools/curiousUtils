package work.curioustools.third_party_network.extensions

import okhttp3.Request


// debuggin extensions for logging
fun Request?.printRequest(tabCount:Int = 0) {
    var tab = "|"
    repeat(tabCount){tab+="/t"}
    println("$tab=====<Request>=====")
    this?.let {
        println("$tab logCurrentCall: body :  ${it.body}")
        println("$tab logCurrentCall: cacheControl ${it.cacheControl}")
        it.headers.toMultimap().forEach { (key, value) -> println("$tab \t $key : $value") }
        println("$tab logCurrentCall: is https: ${it.isHttps}")
        println("$tab logCurrentCall: method ${it.method}")
        println("$tab logCurrentCall: url ${it.url}")
        println("$tab logCurrentCall: tag: ${it.tag()}")
    } ?: println("$tab null")
    println("$tab =====</Request>=====")
}
