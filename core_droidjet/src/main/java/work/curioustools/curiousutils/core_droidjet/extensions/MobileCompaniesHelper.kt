package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.Build
import androidx.annotation.Keep

@Keep
enum class MobileCompaniesHelper {
    SAMSUNG, XIAOMI, REALME, HUAWEI, MOTOROLA, OPPO, VIVO, NOKIA,
    HTC, SONY, LG, MEIZU, ZTE, COOLPAD, LENOVO, GIONEE, SMARTISAN;

    companion object {

        fun getCompanyName(): MobileCompaniesHelper? {
            val brand = Build.MANUFACTURER
            return values().firstOrNull { it.name.equals(brand, ignoreCase = true) }
        }
    }
}
