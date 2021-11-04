package work.curioustools.curiousutils.core_droidjet.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun ViewGroup.rvInflater(): LayoutInflater = LayoutInflater.from(context)

fun ViewGroup.getViewForRecycler(res: Int) = LayoutInflater.from(context).inflate(res, this, false)