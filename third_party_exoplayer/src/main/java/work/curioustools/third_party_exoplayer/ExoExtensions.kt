package work.curioustools.third_party_exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
import com.google.android.exoplayer2.util.Util


fun Context?.agent(fallback: String = "appName"): String {
    this ?: return fallback
    val pkg = this.applicationContext.packageName ?: return fallback
    return Util.getUserAgent(this, pkg)
}


fun String.toMediaItem(builder: MediaItem.Builder = getDefaultMediaItemBuilder()): MediaItem {
    val uri = this.toUri()
    return builder.setUri(uri).build()

}