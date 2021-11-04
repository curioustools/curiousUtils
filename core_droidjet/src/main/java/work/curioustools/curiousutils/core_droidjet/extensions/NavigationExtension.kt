package work.curioustools.curiousutils.core_droidjet.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController


val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()


fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}

fun Fragment.findNullableNavController(): NavController? {
    return try {
        findNavController()
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }
}