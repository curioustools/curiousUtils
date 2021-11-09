package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast


//@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.KITKAT)
//fun isAndroidGTEqualsK19() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.LOLLIPOP)
fun isAndroidGTEquals21L() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isAndroidGTEquals23M() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
fun isAndroidGTEquals24N() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun isAndroidGTEquals26O() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
fun isAndroidGTEquals28P() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P


@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun isAndroidGTEquals29Q() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun isAndroidGTEquals30R() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

//@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
//fun isAndroidGTEqualsS31() = Build.VERSION.SDK_INT >=  31//Build.VERSION_CODES.S

