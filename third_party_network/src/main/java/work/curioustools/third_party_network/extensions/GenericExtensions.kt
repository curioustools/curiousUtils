package work.curioustools.third_party_network.extensions

import com.google.gson.GsonBuilder
import work.curioustools.third_party_network.utils.RetrofitUtils


fun <T> T.toJson(pretty:Boolean = true,serializeNulls:Boolean = true):String{
    val data = this
    return kotlin.runCatching {
        GsonBuilder().run {
            if (pretty) this.setPrettyPrinting()
            if (serializeNulls) this.serializeNulls()
            this
        }.create().toJson(data)
    }.getOrNull()?: "{}"

}

fun <T> Class<T>.getApi(baseUrl: String): T {
    val retrofit = RetrofitUtils.getRetrofit(baseUrl)
    return retrofit.create(this)
}
