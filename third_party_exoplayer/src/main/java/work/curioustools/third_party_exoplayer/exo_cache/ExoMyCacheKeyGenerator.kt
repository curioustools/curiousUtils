package work.curioustools.third_party_exoplayer.exo_cache

import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory

class ExoMyCacheKeyGenerator : CacheKeyFactory {
    override fun buildCacheKey(dataSpec: DataSpec): String {
        return if (dataSpec.key != null) dataSpec.key ?: ""
        else {
            val url = dataSpec.uri.toString()
            val firstParam = url.indexOfFirst { it == '?' }
            if (firstParam > 0) url.substring(0, firstParam)
            else url
        }
    }

}