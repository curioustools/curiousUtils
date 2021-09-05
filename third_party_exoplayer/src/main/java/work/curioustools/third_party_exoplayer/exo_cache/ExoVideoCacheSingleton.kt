package work.curioustools.third_party_exoplayer.exo_cache

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import work.curioustools.third_party_exoplayer.ExoConstants
import java.io.File

//must be called from application as a singleton across the app
object ExoVideoCacheSingleton {
    private var cacheInternal: SimpleCache? = null
    fun getSimpleCacheSingleTon(appCtx: Context): SimpleCache {
        val cacheFolder = File(appCtx.filesDir, "my_app_video_cache")

        val cacheEvictor = LeastRecentlyUsedCacheEvictor(ExoConstants.ONE_GB.toLong()) // My cache size will be 1GB and it will automatically remove least recently used files if the size is reached out. other option is NoOpCacheEvictor, which shall cache all the times unless user's memory runs out

        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(appCtx)
        val legacyIndexSecretKey: ByteArray? = null
        val legacyIndexEncryption = false
        val preferLegacyIndex = false
        val cache = SimpleCache(
            cacheFolder, cacheEvictor, databaseProvider,
            legacyIndexSecretKey, legacyIndexEncryption, preferLegacyIndex
        )
        if(cacheInternal == null) cacheInternal = cache
        return cacheInternal!!
    }
    fun clean() {
        //must be called when app crashes due to cache internal as already locked by some instance(this happens even after app kill) and NEVER OTHERWISE
        cacheInternal = null
    }
}