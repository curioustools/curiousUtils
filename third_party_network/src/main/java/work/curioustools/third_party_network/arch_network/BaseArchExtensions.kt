package work.curioustools.third_party_network.arch_network

import java.util.*


fun <DTO, RESP> BaseResponse<DTO>.convertTo(convertor: (DTO?) -> RESP?): BaseResponse<RESP?> {
    return when (this) {
        is BaseResponse.Failure -> BaseResponse.Failure(
            convertor.invoke(this.body),
            this.statusCode
        )
        is BaseResponse.Success -> BaseResponse.Success(convertor.invoke(this.body))
    }

}

inline fun <T> BaseResponse<T>.printResponse(onSuccess: (T) -> Unit = {}) {
    val time = Calendar.getInstance().time
    println("=====<test started at $time> ========")
    val resp = this
    println("response code =" + resp.statusCode)
    println("response msg= " + resp.statusMsg)

    when (resp) {
        is BaseResponse.Failure -> {
            println("response = ${resp.body}")
            println("exception = ${resp.exception}")
        }
        is BaseResponse.Success -> {
            (resp.body as? BaseDto)?.let {
                println("current response extends BaseDto.class")
                println("error:" + it.error)
                println("limit:" + it.limit)
                println("currentPage:" + it.currentPage)
                println("totalPages:" + it.totalPages)
                println("offset:" + it.offset)
                println("perPageEntries:" + it.perPageEntries)
                println("totalEntries:" + it.totalEntries)
            }
            onSuccess.invoke(resp.body)
        }
    }

    println("=====</end for test started at $time> ========")

}
