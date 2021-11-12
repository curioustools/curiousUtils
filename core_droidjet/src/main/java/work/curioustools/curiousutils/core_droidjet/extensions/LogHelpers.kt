package work.curioustools.curiousutils.core_droidjet.extensions

import android.util.Log

class LogHelpers {
    companion object {
        var libraryLogsEnabled = true
        val tag = "LIB>>"
        val type = LogType.INFO
    }
}


enum class LogType(val type: Int) {
    VERBOSE(Log.VERBOSE),
    DEBUG(Log.DEBUG),
    INFO(Log.INFO),
    WARN(Log.WARN),
    ERROR(Log.ERROR),
    PRINTLN(-1)
}

fun <T> logit(key: String = "", value: T, tag: String = LogHelpers.tag, type: LogType = LogHelpers.type, enabled: Boolean = LogHelpers.libraryLogsEnabled) {
    if (!enabled) return

    val msg = "$key : $value "
    if (type != LogType.PRINTLN) {
        Log.println(type.type, tag, msg)
    }
    else println(msg)
}

fun <T> T.log(name: String = "") {
    logit(key = name, value = this)
}

fun logString(value:String, concat:Any? = null,middle:String = ":" ) {
   if(concat ==null) print(value) else print(  "$value $middle $concat")
}