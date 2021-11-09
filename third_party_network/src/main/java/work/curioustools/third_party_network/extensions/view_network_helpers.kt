package work.curioustools.third_party_network.extensions


import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun AppCompatImageView.loadImageFromInternet(
    url: String,
    @DrawableRes placeholder: Int,
    @DrawableRes error: Int = placeholder,
    @DrawableRes fallback: Int = placeholder,

    ) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .fallback(fallback)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.setImage(
    @DrawableRes res: Int,
    @DrawableRes placeholder: Int = res,
    @DrawableRes error: Int = res,
    @DrawableRes fallback: Int = res,

    ) {
    // very interesting answer regarding why we should not use set image resource or set drawable and rather use glide (specifically with view context):
    // https://stackoverflow.com/q/31964737/7500651 ,
    // https://stackoverflow.com/q/65353510/7500651,
    // https://stackoverflow.com/q/9774705/7500651
    Glide
        .with(this.context)
        .load(res)
        .placeholder(placeholder)
        .error(error)
        .fallback(fallback)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.setImage(
    res: Drawable,
    @DrawableRes placeholder: Int ,
    @DrawableRes error: Int = placeholder,
    @DrawableRes fallback: Int = placeholder,

    ) {
    // very interesting answer regarding why we should not use set image resource or set drawable and rather use glide (specifically with view context):
    // https://stackoverflow.com/q/31964737/7500651 ,
    // https://stackoverflow.com/q/65353510/7500651,
    // https://stackoverflow.com/q/9774705/7500651
    Glide
        .with(this.context)
        .load(res)
        .placeholder(placeholder)
        .error(error)
        .fallback(fallback)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun AppCompatImageView.setImage(
    url: String,
    @DrawableRes placeholder: Int,
    @DrawableRes error: Int = placeholder,
    @DrawableRes fallback: Int = placeholder,
    ) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .fallback(fallback)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
