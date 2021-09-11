package work.curioustools.third_party_network.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

object GsonUtils {
    fun getGsonOrNull(serializeNulls: Boolean =false): Gson? {
        return kotlin.runCatching {
            GsonBuilder().run {
                if (serializeNulls) serializeNulls()
                create()
            }
        }.getOrNull()
    }

    fun getGsonOrError(serializeNulls: Boolean): Gson {
        return getGsonOrNull(serializeNulls)!!
    }

    fun getGsonConvertorOrNull(
        serializeNulls: Boolean = false,
        gson: Gson? = getGsonOrNull(serializeNulls)
    ): GsonConverterFactory? {
        gson ?: return null
        return kotlin.runCatching { GsonConverterFactory.create(gson) }.getOrNull()
    }

    fun getGsonConvertorOrError(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}