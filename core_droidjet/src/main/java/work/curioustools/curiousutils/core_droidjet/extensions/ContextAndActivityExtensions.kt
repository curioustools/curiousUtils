package work.curioustools.curiousutils.core_droidjet.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import work.curioustools.curiousutils.core_droidjet.extensions.extras.Snack
import work.curioustools.curiousutils.core_droidjet.extensions.extras.SystemBarsConfig
import work.curioustools.curiousutils.core_droidjet.extensions.extras.SystemBarsConfig.SystemBarType
import kotlin.concurrent.thread


//Note : Activity extends context. therefore all the functions of context are available in activity

fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.getColorDrawable(@ColorRes res: Int):ColorDrawable{
    return ColorDrawable(getColorCompat(res))
}


fun Context?.getColorCompat(@ColorRes colorRes: Int): Int {
    this ?: return android.R.color.black
    return ContextCompat.getColor(this, colorRes)
}

fun Context?.getColorFromStringOrDefault(colorStr: String, @ColorRes default: Int = android.R.color.black): Int {
    //use: #112233 (#rrggbb or #aarrggbb )format
    this ?: return getColorCompat(default)
    return kotlin.runCatching {
        Color.parseColor(colorStr)
    }.getOrElse {
        it.printStackTrace()
        getColorCompat(default)
    }
}

fun Context?.getColorStateListCompat(@ColorRes colorRes: Int): ColorStateList {
    val default: ColorStateList = ColorStateList.valueOf(Color.MAGENTA)
    this ?: return default
    return ContextCompat.getColorStateList(this, colorRes) ?: default
}

fun Context?.showToast(str: String, length: Int = LENGTH_SHORT) {
    this ?: return
    Toast.makeText(this, str, length).show()
}

fun Context?.isDarkThemeOn(): Boolean {
    this ?: return false
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun Context?.showKeyboard() = getAsView().showKeyboard()

fun Context?.showKeyboardForced() = getAsView().showKeyboardForced()
fun Context?.hideKeyboard() = getAsView().hideKeyboard()
fun Context?.showSnackBar(info: Snack = Snack()) = getAsView()?.showSnackBar(info)


fun Context?.getDrawableFromName(iconName: String): Drawable? {
    this ?: return null
    return kotlin.runCatching {
        val identifier = resources.getIdentifier(iconName, "drawable", packageName)
        ContextCompat.getDrawable(this, identifier)
    }.getOrElse {
        it.printStackTrace()
        null
    }
}

fun Context?.copyToClipboard(text: String) {
    this ?: return
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("label", text)
    clipboard?.setPrimaryClip(clip)
}

fun Context?.isLightColor(@ColorRes colorRes: Int): Boolean {
    val color = getColorCompat(colorRes)
    val customAnswer = Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114 > 160
    //val libAnswer = MaterialColors.isColorLight(color).also { it.log("Lib::") }
    return customAnswer
}

fun Context?.getGradientDrawable(startHashCode: String, endHashCode: String): GradientDrawable {
    val orientation = GradientDrawable.Orientation.LEFT_RIGHT
    val start = getColorFromStringOrDefault(startHashCode)
    val end = getColorFromStringOrDefault(endHashCode)
    return GradientDrawable(orientation, intArrayOf(start, end))
}

fun Context?.dpToPixel(dp: Int): Int {
    this?:return 0
    return (dp * applicationContext.resources.displayMetrics.density).toInt()
}

fun pixelsToDp(px: Int): Int {

    return (px / Resources.getSystem().displayMetrics.density).toInt()
}


// Activity specific extensions---------------------------------------------------------------------------

fun Context?.getAsActivity(): AppCompatActivity? = this as? AppCompatActivity

fun Context?.getAsView(): View? {
    return try {
        val focusedView = getAsActivity()?.currentFocus // will be null if no view is touched
        val screenAsView = getAsActivity()?.findViewById<View>(android.R.id.content)// will be null if called before setContentView()
        focusedView ?: screenAsView
    }
    catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}

fun Context?.finishDelayed(millis: Long) {
    val activity = getAsActivity() ?: return
    thread {
        Thread.sleep(millis)
        activity.runOnUiThread { activity.finish() }
    }
}


fun Context?.findNavControllerByID(fragmentId: Int): NavController {
    val activity = getAsActivity() ?: error("context cannot be casted as appcompat activity. library error on line 82, curiousutils/contextandactivityextension.kt")
    val navHost = activity.supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment
    return navHost.navController
}

fun Context?.findNavControllerByTAG(tag: String): NavController {
    val activity = getAsActivity() ?: error("context cannot be casted as appcompat activity. library error on line 82, curiousutils/contextandactivityextension.kt")
    val navHost = activity.supportFragmentManager.findFragmentByTag(tag) as NavHostFragment
    return navHost.navController
}

fun Context?.configureSystemBar(config: SystemBarsConfig = SystemBarsConfig(), @ColorRes tintDeterminingColor: Int? = config.statusBarColorRes) {
    // this needs to be called in onResume otherwise the flags will get removed, only works above android lollipop
    val activity = getAsActivity() ?: return
    if (!isAndroidGTEquals21L()) return
    val window = activity.window ?: return
    val bars = window.decorView

    when (config.type) {
        SystemBarType.SHOW_ALL_DEFAULT -> {

            // setting system bars to show and clearing previously set "hide me" flags
            window.setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            if (config.statusBarColorRes != null) window.statusBarColor = getColorCompat(config.statusBarColorRes)//setting status color
            if (config.navBarColorRes != null) window.navigationBarColor = getColorCompat(config.navBarColorRes)//setting navbar color

            // setting navbar divider color(28+ only)
            if (isAndroidGTEquals28P() && config.navBarDividerColorRes != null) window.navigationBarDividerColor = getColorCompat(config.navBarDividerColorRes)

            //set tint for all
            configureSystemBarsIconContrast(tintDeterminingColor)
        }
        SystemBarType.FULL_SCREEN_WITH_ICONS -> {
            //todo: this  causes the icons to  show up overlapping your content
            //todo"  does not allow any other config( except  FULL_SCREEN_NO_ICONS) to work,  when called again
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        }
        SystemBarType.FULL_SCREEN_NO_ICONS -> {
            // todo icons   appear again on user interaction
            // todo does not allow any other config to work,  when called again

            when {
                isAndroidGTEquals30R() -> {
                    bars.windowInsetsController?.hide(WindowInsets.Type.systemBars())
                }
                else -> {
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    bars.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                }
            }

        }
    }
}

fun Context?.configureSystemBarsIconContrast(@ColorRes bgColor: Int?) {
    bgColor ?: return
    val bars = getAsActivity()?.window?.decorView ?: return
    val isLightColor = isLightColor(bgColor)
    val whiteIcons = 0
    when {
        isAndroidGTEquals30R() -> {
            val greyNavBarIcons = WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            bars.windowInsetsController?.setSystemBarsAppearance(if (isLightColor) greyNavBarIcons else whiteIcons, greyNavBarIcons)
            val greyStatusBarIcons = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            bars.windowInsetsController?.setSystemBarsAppearance(if (isLightColor) greyStatusBarIcons else whiteIcons, greyStatusBarIcons)
        }
        isAndroidGTEquals26O() -> {
            bars.systemUiVisibility =
                if (isLightColor) bars.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else bars.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        }
        isAndroidGTEquals23M() -> {
            bars.systemUiVisibility =
                if (isLightColor) bars.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else bars.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

}

fun Context?.configureSystemNavBarIconColors(@ColorRes color: Int?) {
    // does not always work. better to set same tint for both status bar and nav bar via configureSystemBarsIconColors
    color ?: return
    val bars = getAsActivity()?.window?.decorView ?: return
    val isLightColor = isLightColor(color)
    val whiteIcons = 0
    when {
        isAndroidGTEquals30R() -> {
            val greyNavBarIcons = WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            bars.windowInsetsController?.setSystemBarsAppearance(if (isLightColor) greyNavBarIcons else whiteIcons, greyNavBarIcons)
        }
        isAndroidGTEquals26O() -> {
            bars.systemUiVisibility = (if (isLightColor) bars.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else bars.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv())
        }
    }

}

fun Context?.configureSystemStatusIconColors(@ColorRes color: Int?) {
    // does not always work. better to set same tint for both status bar and nav bar via configureSystemBarsIconColors

    color ?: return
    val bars = getAsActivity()?.window?.decorView ?: return
    val isLightColor = isLightColor(color)
    val whiteIcons = 0
    when {
        isAndroidGTEquals30R() -> {
            val greyStatusBarIcons = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            bars.windowInsetsController?.setSystemBarsAppearance(if (isLightColor) greyStatusBarIcons else whiteIcons, greyStatusBarIcons)
        }
        isAndroidGTEquals23M() -> {
            bars.systemUiVisibility = (if (isLightColor) bars.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else bars.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        }
    }

}


