package work.curioustools.third_party_network.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory

object GsonUtils {
    fun getGsonObj(serializeNulls: Boolean): Gson? {
        return GsonBuilder().run {
            if (serializeNulls) serializeNulls()
            create()
        }
    }

    fun createGsonConvertor(gson: Gson): GsonConverterFactory? {
        return GsonConverterFactory.create(gson)
    }
}