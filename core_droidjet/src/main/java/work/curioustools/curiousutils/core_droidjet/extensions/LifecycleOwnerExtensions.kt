package work.curioustools.curiousutils.core_droidjet.extensions

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * Lifecycle owner extensions.
 * - extensions on this class helps in providing common functionality to different kinds of ui
 *   components(activity, view , fragment, texture view, etc)
 * - as of now, the extensions are not supporting view  but supporting fragments/activity
 * */

const val NEW_TASK: Int = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
const val CLEAR_TOP: Int = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP


fun LifecycleOwner.launchActivity(destinationIntentWithConfig: Intent, launchOptions: Bundle, requestCode: Int?) {
    when (val owner = this) {
        is AppCompatActivity -> {
            if (requestCode != null) owner.startActivityForResult(destinationIntentWithConfig, requestCode, launchOptions)
            else owner.startActivity(destinationIntentWithConfig, launchOptions)
        }
        is Fragment -> {
            if (requestCode != null) owner.startActivityForResult(destinationIntentWithConfig, requestCode, launchOptions)
            else owner.startActivity(destinationIntentWithConfig, launchOptions)
        }
        else -> {
            // UNDEFINED . if owner is anyone except the activty or fragment, we can't determine the startActivity/startActivityForResult to be used for launching
        }
    }
}


fun LifecycleOwner.launchActivity(
    activityPath: String,
    arguments: Bundle = bundleOf(),
    launchOptions: Bundle = getDefaultLaunchOptions(),
    flags: Int? = null,
    requestCode: Int? = null,
) {
    val packageName = getOwnerContext()?.packageName ?: return

    val launchIntent = Intent(Intent.ACTION_VIEW).also {
        it.setClassName(packageName, activityPath)
        it.putExtras(arguments)
        if (flags != null) it.addFlags(flags)
    }
    launchActivity(launchIntent, launchOptions, requestCode)
}

//Can be used to open activities in a conventional Activity::class.java format. eg : launchActivity<VideoActivity>(bundleOf(EMAIL to inputEmail)
inline fun <reified DEST_ACTIVITY : AppCompatActivity> LifecycleOwner.launchActivity(
    arguments: Bundle = bundleOf(),
    launchOptions: Bundle = getDefaultLaunchOptions(),
    flags: Int? = null,
    requestCode: Int? = null,
) {
    val context = getOwnerContext() ?: return

    val launchIntent = Intent(context, DEST_ACTIVITY::class.java).also {
        it.putExtras(arguments)
        if (flags != null) it.addFlags(flags)
    }
    launchActivity(launchIntent, launchOptions, requestCode)
}


fun LifecycleOwner.getOwnerContext(): Context? {
    return when (this) {
        is AppCompatActivity -> this
        is Fragment -> this.requireContext()
        else -> null // UNDEFINED . if owner is anyone except the activity or fragment, we can't determine the context
    }
}

fun LifecycleOwner.getDefaultLaunchOptions(): Bundle {
    val context = getOwnerContext() ?: return bundleOf()
    val option: ActivityOptions? = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    return option?.toBundle() ?: bundleOf()
}


@Deprecated("use the main function directly", replaceWith = ReplaceWith("launchActivity"))
fun LifecycleOwner.launchNewActivityNewTask(className: String) {
    launchActivity(activityPath = className, flags = NEW_TASK)
}

@Deprecated("use the main function directly", replaceWith = ReplaceWith("launchActivity"))
fun LifecycleOwner.launchNewActivityClearTop(className: String) {
    launchActivity(activityPath = className, flags = CLEAR_TOP)
}
