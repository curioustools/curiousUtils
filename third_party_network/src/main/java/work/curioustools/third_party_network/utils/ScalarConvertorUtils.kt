package work.curioustools.third_party_network.utils

import retrofit2.converter.scalars.ScalarsConverterFactory

object ScalarConvertorUtils {
    @JvmStatic
    fun getConvertorOrNull(): ScalarsConverterFactory? {
        return ScalarsConverterFactory.create()
    }

    @JvmStatic
    fun getConvertorOrError(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }


}