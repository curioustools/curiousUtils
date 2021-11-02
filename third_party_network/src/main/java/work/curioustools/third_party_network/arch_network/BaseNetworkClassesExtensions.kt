package work.curioustools.third_party_network.arch_network

import android.content.Context
import android.widget.Toast

fun <DTO, RESP> BaseResponse<DTO>.convertTo(successConvertor: (DTO) -> RESP, failureConvertor:((DTO?)->RESP?)? = null): BaseResponse<RESP> {
    return when (this) {
        is BaseResponse.Failure -> BaseResponse.Failure(failureConvertor?.invoke(this.body), this.status,this.exception)
        is BaseResponse.Success -> BaseResponse.Success(successConvertor.invoke(this.body))
    }

}

// debuggin extensions for logging
fun <T> BaseResponse<T>.printBaseResponse() {
    println("=====<BaseResponse> ========")
    val resp = this
    println("response status =" + resp.status)
    when (resp) {
        is BaseResponse.Failure -> {
            println("response = ${resp.body}")
            println("exception = ${resp.exception}")
        }
        is BaseResponse.Success -> {
            val body = resp.body
            println("response = ${body}")
            if(body is BaseDto){
                println("current response extends BaseDto.class")
                println("error:" + body.error)
                println("limit:" + body.limit)
                println("currentPage:" + body.currentPage)
                println("totalPages:" + body.totalPages)
                println("offset:" + body.offset)
                println("perPageEntries:" + body.perPageEntries)
                println("totalEntries:" + body.totalEntries)
            }
        }

    }
    println("=====</BaseResponse> ========")

}

// small extension for showing error as a toast
fun <T> BaseResponse.Failure<T>.showAsToast(context: Context) {
    Toast.makeText(context, "${this.status}|| ${this.exception.message}", Toast.LENGTH_SHORT).show()
}