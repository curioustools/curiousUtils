package work.curioustools.third_party_network.arch_network

//<HELPER_CLASSES> =================================================================================
sealed class BaseResponse<T>(
    open val statusCode: Int,
    open var statusMsg: String
) {
    // base class which helps in unification of various network responses

    data class Success<T>(val body: T) : BaseResponse<T>(
        BaseResponseType.SUCCESS.code,
        BaseResponseType.SUCCESS.msg
    )

    data class Failure<T>(
        val body: T? = null,
        override val statusCode: Int,
        var exception: Throwable = Exception(BaseResponseType.getStatusMsgOrDefault(statusCode))
    ) : BaseResponse<T>(statusCode, exception.message?:"" ) {


        override fun toString(): String {
            return "Failure(body=$body, statusCode=$statusCode, statusMsg=${statusMsg}, exception=$exception)"
        }


    }
}