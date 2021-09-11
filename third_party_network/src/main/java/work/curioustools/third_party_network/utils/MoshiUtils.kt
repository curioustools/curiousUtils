package work.curioustools.third_party_network.utils

import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory

object MoshiUtils {

    fun getMoshiOrNull(): Moshi? {
        return kotlin.runCatching { Moshi.Builder().build() }.getOrNull()
    }
    fun getMoshiOrError(): Moshi {
        return getMoshiOrNull()!!
    }

    fun getMoshiConvertorOrNull(moshi: Moshi? = getMoshiOrNull()): MoshiConverterFactory? {
        if(moshi ==null) return null
        return  kotlin.runCatching { MoshiConverterFactory.create(moshi) }.getOrNull()
    }

    fun getMoshiConvertorOrError(moshi: Moshi):MoshiConverterFactory{
        return getMoshiConvertorOrNull(moshi)!!
    }


}