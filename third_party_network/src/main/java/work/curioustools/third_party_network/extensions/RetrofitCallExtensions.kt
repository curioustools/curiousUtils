package work.curioustools.third_party_network.extensions

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import work.curioustools.third_party_network.base_arch.BaseDto
import work.curioustools.third_party_network.base_arch.BaseResponse
import work.curioustools.third_party_network.base_arch.BaseResponseType

fun <T> Call<T>.executeAndUnify(): BaseResponse<T> {
    /**
     * Retrofit provides a response of format Response(isSuccessful:True/False, body:T/null,...)
     * it treats all failures as null . this Response object on its own is enough to know about the
     * json response, but for convenience we can use a unified sealed class for handling high level
     * distinctions,such as success, failure, token expire failure etc.
     * */
    return try {
        val response: Response<T?> = execute()
        when {
            response.isSuccessful -> {
                when (val body = response.body()) {
                    null -> BaseResponse.Failure(
                        body,
                        BaseResponseType.APP_NULL_RESPONSE_BODY.code
                    )
                    else -> {
                        // there can be other scenarios also, like
                        //if(body is BaseDto && body.success == false){
                        //  BaseResponse.Failure(null,AppResponseStatus.UNRECOGNISED.code)
                        //}

                        BaseResponse.Success(body)
                    }
                }
            }
            else -> {
                val code = response.code()
                val msg =
                    BaseResponseType.getStatusMsgOrDefault(code)
                // todo get server msg via error body and not from enum
                val body = response.body()
                if (body is BaseDto && body.error.isNullOrBlank()) {
                    // if body is of type BaseDto(which it should be), it will set
                    // error msg if not already set
                    body.error = msg
                }
                val finalResponse = BaseResponse.Failure(body, code)
                finalResponse.statusMsg = msg
                finalResponse.exception = Exception(msg)
                finalResponse
            }
        }
    } catch (t: Throwable) {
        BaseResponse.Failure(null, BaseResponseType.UNRECOGNISED.code,t)
    }

}
fun <T> Call<T>.logCurrentCall(msg:String =""){
    val TAG = "RETROFIT>>"
    Log.e(TAG, "logCurrentCall() called with: msg = $msg")
    request().let {
        Log.e(TAG, "logCurrentCall: body :  ${it.body}")
        Log.e(TAG, "logCurrentCall: cacheControl ${it.cacheControl}")
        it.headers.toMultimap().forEach { (key, value) -> Log.e(TAG, "\t $key : $value") }
        Log.e(TAG, "logCurrentCall: ishttps ${it.isHttps}")
        Log.e(TAG, "logCurrentCall: method ${it.method}")
        Log.e(TAG, "logCurrentCall: url ${it.url}")
    }

}