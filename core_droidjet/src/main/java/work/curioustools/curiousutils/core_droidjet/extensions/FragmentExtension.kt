package work.curioustools.curiousutils.core_droidjet.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import work.curioustools.curiousutils.core_droidjet.extensions.extras.Snack

fun Fragment?.showKeyboard() = this?.context.showKeyboard()
fun Fragment?.showKeyboardForced() = this?.context?.showKeyboardForced()
fun Fragment?.hideKeyboard() = this?.context?.hideKeyboard()

fun Fragment?.showToast(str:String) = this?.context?.showToast(str)
fun Fragment?.showSnackBar(info: Snack = Snack())  = this?.context?.showSnackBar(info)

fun Fragment?.isFragmentVisibleForInteraction(checkForActivityToo: Boolean = true): Boolean {
    this?:return false
    return if (checkForActivityToo) {
        val activityVisible = activity?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED)
        isAdded && isVisible && userVisibleHint && activityVisible == true
    } else isAdded && isVisible && userVisibleHint
}


