package work.curioustools.core_jdk

import java.util.*

class DateTimeUtils {
    companion object {

        fun getUserTimeZone(): String {
            return Calendar.getInstance().timeZone.getOffset(Date().time).toString()
        }
    }
}