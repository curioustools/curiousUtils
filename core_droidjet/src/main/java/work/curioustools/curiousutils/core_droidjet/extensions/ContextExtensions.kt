package work.curioustools.curiousutils.core_droidjet.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

//Note : Activity extends context. therefore all the functions of context are available in activity

fun Context.contextAsView(): View {
    return if (this is AppCompatActivity) this.currentFocus ?: View(this) else View(this)
}

fun Context.getDrawableCompat(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context?.getColorCompat(@ColorRes colorRes: Int): Int {
    this ?: return android.R.color.black
    return ContextCompat.getColor(this, colorRes)
}


fun Context?.showToast(str: String, length: Int = LENGTH_SHORT) {
    this ?: return
    Toast.makeText(this, str, length).show()
}

fun Context?.showSnackBar(view: View? = null, msg: String = "", @StringRes msgRes: Int = -1, length: Int = Snackbar.LENGTH_SHORT) {
    this ?: return
    val finalView = view ?: contextAsView()
    val finalMsg = if (msgRes == -1) msg else getString(msgRes)
    Snackbar.make(finalView, finalMsg, length).show()
}

fun Context?.isDarkThemeOn(): Boolean {
    this ?: return false
    return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}


fun Context?.showKeyboard() {
    this ?: return
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Context?.showKeyboardForced(view: View? = null) {
    this ?: return
    val finalView = view ?: contextAsView()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(finalView, InputMethodManager.SHOW_FORCED)
}

fun Context?.hideKeyboard(view: View? = null) {
    this ?: return
    val finalView = view ?: contextAsView()
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(finalView.windowToken, 0)

}
