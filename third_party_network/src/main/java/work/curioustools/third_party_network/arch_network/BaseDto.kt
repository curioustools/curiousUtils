package work.curioustools.third_party_network.arch_network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

open class BaseDto(
    @field:Json(name = "error") @SerializedName("error")
    open var error: String? = null,

    @field:Json(name = "page") @SerializedName("page")
    open val currentPage: Int? = null,

    @field:Json(name = "total_pages") @SerializedName("total_pages")
    open val totalPages: Int? = null,

    @field:Json(name = "per_page") @SerializedName("per_page")
    open val perPageEntries: Int? = null,

    @field:Json(name = "total") @SerializedName("total")
    open val totalEntries: Int? = null,

    @field:Json(name = "limit") @SerializedName("limit")
    val limit: Int? = null,

    @field:Json(name = "offset") @SerializedName("offset")
    val offset: Int? = null,

    @field:Json(name = "success") @SerializedName("success")
    val success: Boolean? = null

) {
    /**
     * we can use this class as parent for list based dtos. i.e dtos which map the response of
     * api calls that returns a response like this:
    {
    "page":1,
    "total_pages":35,
    "total":3500,
    "per_page":100,
    "data":[ {object},{object},{object},...]
    }

    your dto will look very clean, like this:

    @Keep
    data class AllAuthUsersResponseDto(
    @field:Json(name = "data") val entries: List<UserDto>? = null
    ) : BaseDto(){

    @Keep
    data class UserDto(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "email") val email: String? = null,
    ...
    )
    }
     *
     *
     *
     * */
}