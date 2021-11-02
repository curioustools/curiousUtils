package work.curioustools.third_party_network.arch_network

import retrofit2.Call
import retrofit2.http.GET
import work.curioustools.third_party_network.extensions.executeAndUnify
import work.curioustools.third_party_network.extensions.getRetrofitBuilder
import work.curioustools.third_party_network.utils.addGsonConvertor
import work.curioustools.third_party_network.utils.addScalerConvertor
import work.curioustools.third_party_network.utils.getMyOkHttpBuilderBasic


class RetrofitHelpersArchKtTest {

    interface Api {

        @GET(PATH)
        fun getString(): Call<String>

        companion object {
            const val URL = "https://joeysbookshop.herokuapp.com/api/bookist/Italian"
            const val BASE = "https://joeysbookshop.herokuapp.com/"
            const val PATH = "api/booklist/Italian"
        }
    }



}

fun testExecuteAndUnify() {
    val client = getMyOkHttpBuilderBasic().build()
    val retrofit = getRetrofitBuilder(RetrofitHelpersArchKtTest.Api.BASE, client).addScalerConvertor().addGsonConvertor().build()
    val data = retrofit.create(RetrofitHelpersArchKtTest.Api::class.java).getString().executeAndUnify(true)
    data.printBaseResponse()

}
fun main(args: Array<String>) {
    testExecuteAndUnify()
}