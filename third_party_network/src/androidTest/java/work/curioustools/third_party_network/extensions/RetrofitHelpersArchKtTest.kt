package work.curioustools.third_party_network.extensions

import junit.framework.TestCase
import retrofit2.Call
import retrofit2.http.GET
import work.curioustools.third_party_network.utils.getMyOkHttpBuilderBasic
import work.curioustools.third_party_network.utils.getMyUnsafeOkHttpBuilderDEBUG

class RetrofitHelpersArchKtTest : TestCase() {

    interface Api{

        @GET(PATH)
        fun getString(): Call<String>

        companion object{
            const val URL = "https://joeysbookshop.herokuapp.com/api/booklist/Italian"
            const val BASE = "https://joeysbookshop.herokuapp.com/"
            const val PATH = "api/booklist/Italian"
        }
    }

    
    fun testExecuteAndUnify() {
        val client = getMyOkHttpBuilderBasic().build()
        val retrofit = getRetrofitBuilder(Api.BASE,client).build()
        retrofit.create(Api::class.java).getString().executeAndUnify(true)

    }
}