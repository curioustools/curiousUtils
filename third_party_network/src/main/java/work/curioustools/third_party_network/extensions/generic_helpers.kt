package work.curioustools.third_party_network.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


inline fun <reified T> T.toJsonString(gson: Gson = getGsonObject(getGsonBuilder(pretty = false)), type: Type = object : TypeToken<T>() {}.type): String {
    if (this == null) return "{null}"
    return try {
        gson.toJson(this, type) ?: "{null string}"
    }
    catch (t: Throwable) {
        "{error: ${t}}"
    }
}

inline fun <reified T> String.toObjectOrCrash(gson: Gson = getGsonObject(), type: Type = object : TypeToken<T>() {}.type): T {
    return gson.fromJson(this, type)
}

inline fun <reified T> String.toObjectOrNull(gson: Gson = getGsonObject(), type: Type = object : TypeToken<T>() {}.type): T? {
    return try {
        gson.fromJson(this, type)
    }
    catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}