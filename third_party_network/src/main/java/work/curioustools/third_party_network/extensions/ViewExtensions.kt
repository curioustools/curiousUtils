package work.curioustools.third_party_network.extensions

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