package work.curioustools.third_party_exoplayer.exo_cache

import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory

class ExoMyCacheKeyGenerator : CacheKeyFactory {
    override fun buildCacheKey(dataSpec: DataSpec): String {
        //if key is available, return it otherwise return the url till first '?' . thus cache will be for only url and not the params that usually change: http://www.abc.xyz/vid/1234.mp4?x=y
        return if (dataSpec.key != null) dataSpec.key ?: ""
        else {
            val url = dataSpec.uri.toString()
            val firstParam = url.indexOfFirst { it == '?' }
            if (firstParam > 0) url.substring(0, firstParam)
            else url
        }
    }


}