package work.curioustools.third_party_network.extensions

import com.google.gson.reflect.TypeToken
import work.curioustools.third_party_network.utils.getGsonObject
import work.curioustools.third_party_network.utils.getGsonBuilder
import java.lang.reflect.Type


inline fun <reified T> T.toJsonString(pretty: Boolean = false, type: Type = object : TypeToken<T>() {}.type, ): String {
    val gson = getGsonObject(getGsonBuilder(pretty = pretty))
    if (this == null) return "{null}"
    return try { gson.toJson(this, type) ?: "{null string}" }
    catch (t: Throwable) { "{error: ${t}}" }
}

inline fun <reified T> String.toKotlinObject(type: Type = object : TypeToken<T>() {}.type): T {
    val gson = getGsonObject()
    return gson.fromJson(this, type)
}
