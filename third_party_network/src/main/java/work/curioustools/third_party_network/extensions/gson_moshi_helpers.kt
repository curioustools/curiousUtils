package work.curioustools.third_party_network.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


//--gson--

fun getGsonBuilder(serializeNulls: Boolean = false, pretty: Boolean = false, ): GsonBuilder {
    val builder = GsonBuilder()
    if (serializeNulls) builder.serializeNulls()
    if (pretty) builder.setPrettyPrinting()
    return builder
}

fun getGsonObject(builder: GsonBuilder = getGsonBuilder()): Gson = builder.create()

fun Retrofit.Builder.addGsonConvertor(gson: Gson = getGsonObject()): Retrofit.Builder {
    addConverterFactory(GsonConverterFactory.create(gson))
    return this
}

//--moshi--

fun getMoshiBuilder(): Moshi.Builder = Moshi.Builder()
fun getMoshiObject(builder: Moshi.Builder = getMoshiBuilder()): Moshi = builder.build()

fun Retrofit.Builder.addMoshiConvertor(moshi: Moshi = getMoshiObject()): Retrofit.Builder {
    addConverterFactory(MoshiConverterFactory.create(moshi))
    return this
}

//--scalars
fun Retrofit.Builder.addScalerConvertor(): Retrofit.Builder {
    addConverterFactory(ScalarsConverterFactory.create())
    return this
}