package work.curioustools.third_party_network.extensions


import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


@Keep
data class GlideBuilderOptions(
    @DrawableRes val placeholder: Int,
    @DrawableRes val error: Int = placeholder,
    @DrawableRes val fallback: Int = placeholder,
    val minifiedThumbnail: Float? = 0.5f,
    val loadingAnimation: DrawableTransitionOptions? = DrawableTransitionOptions.withCrossFade(),
    val addListener: RequestListener<Drawable?>? = null,

    )

fun RequestBuilder<Drawable?>.setBuilderOptions(options: GlideBuilderOptions?): RequestBuilder<Drawable?> {
    options ?: return this
    val placeholderEnabledRB = placeholder(options.placeholder)
    val errorEnabledRB = placeholderEnabledRB.error(options.error)
    val fallbackEnabledRB = errorEnabledRB.fallback(options.fallback)
    val animationRB = options.loadingAnimation?.let { fallbackEnabledRB.transition(it) } ?: fallbackEnabledRB.dontAnimate()
    val thumbnailRB = options.minifiedThumbnail?.let { animationRB.thumbnail(it) } ?: animationRB
    val listenerEnabledRB = options.addListener?.let { thumbnailRB.addListener(it) } ?: thumbnailRB
    return listenerEnabledRB
}


fun Context?.preLoadUrl(url: String) {
    this ?: return
    Glide.with(applicationContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).preload()
}


fun Context?.isValidForGlideUI(): Boolean {
    this ?: return false
    return if (this is Activity) !isDestroyed && !isFinishing
    else false

}

// interesting answer regarding why we should not use set image resource or set drawable and rather use glide (specifically with view context): https://stackoverflow.com/q/31964737/7500651 , // https://stackoverflow.com/q/65353510/7500651, // https://stackoverflow.com/q/9774705/7500651
//1. must pass glideRM for bottomsheets as glide gets null as context when used with view context. DO NOT PASS FOR FRAGMENTS ELSE WILL ADD AN ADDITIONAL  headless FRAGMENT.
//2. placeholder height/width will limit the height width of your imageview if imageview's  height/width is wrap content. use a sufficiently  big placeholder if you want a fine control over loaded image height/width
fun ImageView?.setImage(@DrawableRes res: Int, options: GlideBuilderOptions? = GlideBuilderOptions(res), rm: RequestManager? = null) {
    this ?: return
    if (context.isValidForGlideUI())
        (rm ?: Glide.with(context)).load(res).setBuilderOptions(options).into(this)
}

fun ImageView?.setImage(res: Drawable, options: GlideBuilderOptions? = null, rm: RequestManager? = null) {
    this ?: return
    if (context.isValidForGlideUI())
        (rm ?: Glide.with(context)).load(res).setBuilderOptions(options).into(this)
}

fun ImageView?.setImage(res: String, options: GlideBuilderOptions? = null, rm: RequestManager? = null) {
    this ?: return
    if (context.isValidForGlideUI())
        (rm ?: Glide.with(context)).load(res).setBuilderOptions(options).into(this)
}


fun View?.setBackground(res: String, options: GlideBuilderOptions? = null, rm: RequestManager? = null) {
    this ?: return
    val customTarget = object : CustomTarget<Drawable?>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) { this@setBackground.background = resource }
        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    if (context.isValidForGlideUI())
            (rm ?: Glide.with(context)).load(res).setBuilderOptions(options).into(customTarget)
}