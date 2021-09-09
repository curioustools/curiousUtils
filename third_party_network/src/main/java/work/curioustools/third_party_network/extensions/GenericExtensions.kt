package work.curioustools.third_party_network.extensions

import com.google.gson.GsonBuilder
import work.curioustools.third_party_network.utils.RetrofitUtils


fun <T> T.toJson(pretty:Boolean = true,serializeNulls:Boolean = true):String{
    return try {
        GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(this)
    }
    catch (t:Throwable){
        t.printStackTrace()
        "{}"
    }
}

fun <T> Class<T>.getApi(baseUrl: String): T {
    val retrofit = RetrofitUtils.getRetrofit(baseUrl)
    return retrofit.create(this)
}
