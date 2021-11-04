package work.curioustools.curiousutils.core_droidjet.extensions

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar

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

fun View?.showKeyboard() = this?.context.showKeyboard()
fun View?.hideKeyboard() = this?.context.hideKeyboard(this)
fun View?.showKeyboardForced() = this?.context?.showKeyboardForced(this)
fun View?.showToast(str: String) = this?.context?.showToast(str)

fun View?.showSnackBar(msg: String = "", @StringRes msgRes: Int = -1, length: Int = Snackbar.LENGTH_SHORT) = this?.context?.showSnackBar(this, msg, msgRes, length)
