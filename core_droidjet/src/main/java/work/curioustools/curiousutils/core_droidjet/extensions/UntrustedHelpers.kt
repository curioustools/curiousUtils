package work.curioustools.curiousutils.core_droidjet.extensions


import android.content.res.Resources



fun screenHeight(): Int { return Resources.getSystem().displayMetrics.heightPixels }

fun screenWidth(): Int { return Resources.getSystem().displayMetrics.widthPixels }

