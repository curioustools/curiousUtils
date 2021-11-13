package work.curioustools.curiousutils.core_droidjet.extensions

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textview.MaterialTextView
import work.curioustools.curiousutils.core_droidjet.extensions.extras.Snack
import java.util.*


fun View?.isVisible(): Boolean = this?.visibility == View.VISIBLE
fun View?.isGone(): Boolean = this?.visibility == View.GONE
fun View?.isInvisible(): Boolean = this?.visibility == View.INVISIBLE

fun View?.setVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.setGone() {
    this?.visibility = View.GONE
}

fun View?.setInvisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.rotateSmoothly(from: Number = 0, to: Number = 90, animSpeedMillis: Long = 100, fillAfter: Boolean = true) {
    this ?: return
    RotateAnimation(from.toFloat(), to.toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).run {
        duration = animSpeedMillis
        this.fillAfter = fillAfter
        startAnimation(this)
    }
}
fun View?.setAlphaAnimated(initial:Float =1f, final:Float = 0f, duration:Long =1000){
    this?:return
    alpha = initial
    val animation = AlphaAnimation(initial, final)
    animation.duration = duration
    animation.fillAfter = true
    startAnimation(animation)
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


fun View?.setDrawableForTextBasedViews(@DrawableRes left: Int = 0, @DrawableRes top: Int = 0, @DrawableRes right: Int = 0, @DrawableRes bottom: Int = 0) {
    this?:return

    val view = when (this) {
        is AppCompatButton -> this as AppCompatButton
        is MaterialTextView -> this as MaterialTextView
        is TextView -> this as TextView
        is AppCompatEditText -> this as AppCompatEditText
        else -> return
    }
    when {
        isAndroidGTEquals21L() -> view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)

        else -> {
            view.setCompoundDrawablesWithIntrinsicBounds(
                createCompatVectorDrawable(left),
                createCompatVectorDrawable(top),
                createCompatVectorDrawable(right),
                createCompatVectorDrawable(bottom)
            )
        }
    }
}

fun View?.createCompatVectorDrawable(@DrawableRes res: Int=0): Drawable? {
    this ?: return null
    if(res == 0) return null
    return VectorDrawableCompat.create(context.resources, res, context.theme)
}

fun TextView?.setTextOrHide(str:String?){
    this?:return
    if(str!=null){
        setVisible()
        text = str
    }
    else setGone()
}

fun TextView?.setTextOrInvisible(str:String?){
    this?:return
    if(str!=null){
        setVisible()
        text = str
    }
    else setInvisible()
}

fun TextView?.setHTMLStringAsText(htmlString: String) {
    val spannedString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlString)
    }
    this?.text = spannedString
}

@SuppressLint("ClickableViewAccessibility")
fun EditText?.enableInternalScroll() {
    // when edittext is in a scrollable container, we can't scroll its inside contents . without making the outside container scroll. this fixes that issue and allows us to scroll edittext content without scrolling ite
    this?.setOnTouchListener { v: View?, event: MotionEvent? ->
        v?.parent?.requestDisallowInterceptTouchEvent(true)
        event?.let {
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v?.parent?.requestDisallowInterceptTouchEvent(false)
                else -> {

                }
            }
        }
        false
    }
}

fun RecyclerView?.scrollToBottomIfApplicable() {
    this?:return
    if (!canScrollVertically(1)) {
        this.post {
            (this.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
        }
    }
}

fun ProgressBar.setAnimatedProgress(progressPercent: Int) {
    ObjectAnimator.ofInt(this, "progress", progressPercent).setDuration(300).start()
}

fun View?.setHeight(aspectRatio: Float = 0.4f, margin: Int = 0) {
    this?:return
    val marginInPx = this.context.dpToPixel(margin)
    val params = this.layoutParams
    params.height = ((Resources.getSystem().displayMetrics.widthPixels + marginInPx) * aspectRatio).toInt()
    this.layoutParams = params
}


fun View?.setSafeOnClickListener(defaultInterval: Int = 1000, onSafeClick: (View) -> Unit) {
    this?:return
    val safeClickListener = SafeClickListener(defaultInterval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}


fun View?.showPopupMenu(menuRes: Int, listener: PopupMenu.OnMenuItemClickListener? = null, gravity: Int = Gravity.START) {
    this?.apply {
        val popup = PopupMenu(context, this, gravity)
        popup.inflate(menuRes)
        listener?.let { popup.setOnMenuItemClickListener(it) }
        popup.show()
    }
}

//takes margin values as integer , eg for 12dp top , you will pass 12
fun View?.setMarginFromConstant(mLeft: Int, mTop: Int, mRight: Int, mBottom: Int) {
    this?.apply {
        val left = context?.dpToPixel(mLeft) ?: 0
        val top = context?.dpToPixel(mTop) ?: 0
        val right = context?.dpToPixel(mRight) ?: 0
        val bottom = context?.dpToPixel(mBottom) ?: 0
        when (val params = this.layoutParams) {
            is ConstraintLayout.LayoutParams -> {
                params.marginStart = left
                params.marginEnd = right
                params.topMargin = top
                params.bottomMargin = bottom
            }
            is FrameLayout.LayoutParams -> {
                params.marginStart = left
                params.marginEnd = right
                params.topMargin = top
                params.bottomMargin = bottom
            }
            is RecyclerView.LayoutParams -> {
                params.marginStart = left
                params.marginEnd = right
                params.topMargin = top
                params.bottomMargin = bottom
            }
        }
    }

}

fun TabLayout?.makeSelectedTabAsBold() {
    this?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.let { setStyleForTab(it, Typeface.NORMAL) }
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let { setStyleForTab(it, Typeface.BOLD) }
        }

        fun setStyleForTab(tab: TabLayout.Tab, style: Int) {
            tab.view.children.find { it is TextView }?.let { tv -> (tv as? TextView)?.post { tv.setTypeface(null, style) } }
        }
    })
}


