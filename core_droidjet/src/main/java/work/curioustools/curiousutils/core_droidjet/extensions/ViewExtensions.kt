package work.curioustools.curiousutils.core_droidjet.extensions

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar
import work.curioustools.curiousutils.core_droidjet.extensions.models.Snack

fun Context?.launchCustomTabWebView(url:String){
    this?:return
    CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))
}

fun View?.isVisible():Boolean = this?.visibility == View.VISIBLE
fun View?.isGone():Boolean = this?.visibility == View.GONE
fun View?.isInvisible():Boolean = this?.visibility == View.INVISIBLE

fun View?.setVisible(){
    this?.visibility = View.VISIBLE
}

fun View?.setGone(){
    this?.visibility = View.GONE
}

fun View?.setInvisible(){
    this?.visibility = View.INVISIBLE
}

fun View?.rotateSmoothly(from:Number = 0 , to: Number = 90, animSpeedMillis:Long = 100 , fillAfter:Boolean = true ){
    this?:return
    RotateAnimation(from.toFloat(), to.toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).run {
        duration = animSpeedMillis
        this.fillAfter = fillAfter
        startAnimation(this)
    }

}

fun View?.showKeyboard() {
    if (this == null || context == null) return
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View?.hideKeyboard() {
    if (this == null || context == null) return
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

}

fun View?.showKeyboardForced() {
    if (this == null || context == null) return
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}


fun View?.showToast(str: String) = this?.context?.showToast(str)

fun View?.showSnackBar(info: Snack = Snack()) {
    this ?: return
    val finalMsg = if (info.msgRes == -1) info.msg else context.getString(info.msgRes)
    Snackbar.make(this, finalMsg, info.length).show()
}