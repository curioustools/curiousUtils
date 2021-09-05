package work.curioustools.third_party_exoplayer

import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory


fun String.toMediaItem(): MediaItem {
    return MediaItem.fromUri(this)
}

fun Uri?.toMediaItem(cacheKeyFactory: CacheKeyFactory?): MediaItem {
    this ?: error("From Ansh :: Uri is null")
    return MediaItem.Builder().let {
        if(cacheKeyFactory!=null) {
            val dataSpec = DataSpec(this)
            val key = cacheKeyFactory.buildCacheKey(dataSpec)
            it.setCustomCacheKey(key)
        }
        it.setUri(this)
        it.build()
    }
}
