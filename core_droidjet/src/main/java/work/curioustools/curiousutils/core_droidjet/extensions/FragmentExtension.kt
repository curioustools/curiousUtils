package work.curioustools.curiousutils.core_droidjet.extensions

import androidx.fragment.app.Fragment
import work.curioustools.curiousutils.core_droidjet.extensions.models.Snack

fun Fragment?.showKeyboard() = this?.context.showKeyboard()
fun Fragment?.showKeyboardForced() = this?.context?.showKeyboardForced()
fun Fragment?.hideKeyboard() = this?.context?.hideKeyboard()

fun Fragment?.showToast(str:String) = this?.context?.showToast(str)
fun Fragment?.showSnackBar(info: Snack = Snack())  = this?.context?.showSnackBar(info)

