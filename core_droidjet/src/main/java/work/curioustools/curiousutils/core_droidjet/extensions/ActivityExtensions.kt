package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.color.MaterialColors
import kotlin.concurrent.thread


//activity extensions.note: a lot of extensions are inside context extensions because activity extends context
fun AppCompatActivity.finishDelayed(millis: Long) {
    thread {
        Thread.sleep(millis)
        runOnUiThread { finish() }
    }
}

//https://stackoverflow.com/a/66492857/7500651
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.setSystemStatusBarColor(@ColorRes res: Int) {//todo verify
    val window = this.window ?: return
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColorCompat(res)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.setSystemBottomNavBarColor(@ColorRes barColor: Int, setContrastingNavIcons: Boolean = true) {//todo verify
    val barColorCode = getColorCompat(barColor)
    val window = this.window ?: return
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.navigationBarColor = barColorCode
    if (setContrastingNavIcons && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) updateSystemNavbarNavBarIconTint(barColor)

}
@RequiresApi(Build.VERSION_CODES.O)
fun AppCompatActivity.updateSystemNavbarNavBarIconTint(@ColorRes barColor: Int) {
    val resolvedColor = getColorCompat(barColor)
    window.decorView.systemUiVisibility =
        if (MaterialColors.isColorLight(resolvedColor)) (window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        else (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv())

}

fun AppCompatActivity.setStatusBarIconColorAsWhite(setAsWhite: Boolean = isDarkThemeOn()) {
    window.decorView.rootView.systemUiVisibility = if (setAsWhite) 0 else 8192
}


fun AppCompatActivity.toggleActivityActionBar(show: Boolean) {
    val actionBar = supportActionBar ?: return
    if (show) {
        actionBar.show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window?.setDecorFitsSystemWindows(false)
        else window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    else actionBar.hide()
}

fun AppCompatActivity.hideStatusBarActionBarAndBottomNav() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window?.setDecorFitsSystemWindows(false)
    }
    else {
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) or (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}
// todo difference b/w full screen and immersive?
fun AppCompatActivity.makeUIGoImmersive() {
   val window = window?:return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) window.setDecorFitsSystemWindows(false)
    else window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

}

fun AppCompatActivity.findNavControllerByID(fragmentId: Int): NavController {
    val navHost = supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment
    return navHost.navController
}

fun AppCompatActivity.findNavControllerByTAG(tag: String): NavController {
    val navHost = supportFragmentManager.findFragmentByTag(tag) as NavHostFragment
    return navHost.navController
}
