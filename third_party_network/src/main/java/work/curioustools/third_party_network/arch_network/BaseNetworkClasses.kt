package work.curioustools.third_party_network.arch_network

import androidx.annotation.Keep
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Keep
open class BaseDto(
    @field:Json(name = "error") @SerializedName("error") open var error: String? = null,
    @field:Json(name = "page") @SerializedName("page") open val currentPage: Int? = null,
    @field:Json(name = "total_pages") @SerializedName("total_pages") open val totalPages: Int? = null,
    @field:Json(name = "per_page") @SerializedName("per_page") open val perPageEntries: Int? = null,
    @field:Json(name = "total") @SerializedName("total") open val totalEntries: Int? = null,
    @field:Json(name = "limit") @SerializedName("limit") val limit: Int? = null,
    @field:Json(name = "offset") @SerializedName("offset") val offset: Int? = null,
    @field:Json(name = "success") @SerializedName("success") val success: Boolean? = null,
)

//<HELPER_CLASSES> =================================================================================
@Keep
sealed class BaseResponse<T>(open val status: BaseStatus) {

    @Keep
    data class Success<T>(val body: T) : BaseResponse<T>(BaseStatus.SUCCESS)

    @Keep
    data class Failure<T>(val body: T? = null, override val status: BaseStatus, var exception: Throwable = Exception(status.msg)) : BaseResponse<T>(status)
}

@Keep
enum class BaseStatus(val code: Int, val msg: String) {
    SUCCESS(200, "SUCCESS"),
    NO_INTERNET_CONNECTION(1001, "No Internet found"),
    NO_INTERNET_PACKETS_RECEIVED(1002, "We are unable to connect to our server. Please check with your internet service provider"),
    USER_NOT_FOUND(400, "User Not Found"),
    APP_NULL_RESPONSE_BODY(888, "No Response found"),
    SERVER_FAILURE(500, "server failure"),
    SERVER_DOWN_502(502, "server down 502"),
    SERVER_DOWN_503(503, "server down 503"),
    SERVER_DOWN_504(504, "server down 504"),
    UNRECOGNISED(-1, "unrecognised error in networking");

    companion object {
        fun getStatusOrDefault(code: Int? = null): BaseStatus = values().firstOrNull { it.code == code } ?: UNRECOGNISED

        fun getStatusFromException(t: Throwable): BaseStatus = values().firstOrNull { it.msg.contentEquals(t.message) } ?: UNRECOGNISED

    }
}

abstract class BaseConcurrencyUseCase<REQUEST, RESP> {
    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    val liveData = MutableLiveData<RESP>()

    abstract suspend fun getRepoCall(param: REQUEST): RESP

    fun requestForData(param: REQUEST) {
        scope.apply {
            launch(Dispatchers.IO + job) {
                val result: RESP? = getRepoCall(param)
                result?.let { liveData.postValue(it) }
            }
        }
    }

}