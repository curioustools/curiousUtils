package work.curioustools.third_party_network.utils

import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory

object MoshiUtils {
    fun getMoshiObj(): Moshi? {
        return Moshi.Builder().build()
    }

    fun getMoshiConvertorOrNull(): MoshiConverterFactory? {
        val mosh = getMoshiObj() ?: return null
        return MoshiConverterFactory.create(mosh)
    }

    fun getMoshiConvertorOrError(): MoshiConverterFactory {
        return MoshiConverterFactory.create(getMoshiObj()!!)
    }


}