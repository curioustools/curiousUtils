package work.curioustools.third_party_network.extensions

import com.google.gson.Gson

fun <T> String.toKotlinModel(clazz:Class<T>):T?{
    return try {
        Gson().fromJson(this,clazz)
    }
    catch (t:Throwable){
        t.printStackTrace()
        null
    }
}
