package work.curioustools.third_party_exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import work.curioustools.third_party_exoplayer.exo_cache.ExoMyCacheKeyGenerator


// current strategy. don't complicate stuff.
// choose defaults on your own without giving the power to caller. giving power is good in large projects
// only request for necessary stuff. pass shared values
class ExoObjects {
    companion object {
        fun getUserAgent(ctx: Context?=null):String{
            return if(ctx ==null) "agent" else Util.getUserAgent(ctx, "appName")
        }

        fun getHttpDSF(agent:String): HttpDataSource.Factory {
            // an http data source factory is like a component of a big pipe which works on the network layer, for fetching of data packets from the server to user device renderer view
            val factory =  DefaultHttpDataSource.Factory().let {
                it.setTransferListener(null)
                it.setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                it.setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                it.setUserAgent(agent)
                it.setAllowCrossProtocolRedirects(true)
                it.setContentTypePredicate(null)
            }
            return factory
        }

        fun getDefaultDSF(ctx: Context, httpDSF: HttpDataSource.Factory): DefaultDataSourceFactory {
            // a default data source factory is like a generic pipe that can fetch data packets from any layer : from server to  renederer view, from files to renderer view , from some memory block to renderer view, etc. it is best to use as it is completely customizable, meaning we can pass our own http data source, file data source , and it will also implemt several optimisations.
            val listener: TransferListener? = null
            return DefaultDataSourceFactory(ctx, listener, httpDSF)

        }

        fun getCacheDSF(cache: SimpleCache, httpDSF: HttpDataSource.Factory, cacheKeyGenerator: ExoMyCacheKeyGenerator): CacheDataSource.Factory {
            // Build data source factory with cache enabled, if data is available in cache it will return immediately and use cache, otherwise it will open a new connection to get the data.
            // note: the cache must be a singleton across the app, thus its best to initialise it in application subclass
            val factory =  CacheDataSource.Factory().let {
                it.setCache(cache)
                it.cacheKeyFactory = cacheKeyGenerator
                it.setUpstreamDataSourceFactory(httpDSF)
            }
            return factory
        }

        fun getMediaSource(url: String, dsf: DataSource.Factory, cacheKeyGenerator: ExoMyCacheKeyGenerator): MediaSource {
            //param info: dsf could be cache data source factory or default data source factory .return type info : Progressive media source
            val uri = Uri.parse(url)

            return ProgressiveMediaSource.Factory(dsf).let {
                it.createMediaSource(uri.toMediaItem(cacheKeyGenerator))
            }

        }
        fun getPreFetchLoadController() : DefaultLoadControl {
            // this is a useful object to customise the properties of an exoplayer's abstract player.. it determines hw much bufferring will be done for forward/backward  press. my app has large(>1gb) videos, so i think bufferring for 5 minutes is optimal, but for other apps, default options are correct too.
            return DefaultLoadControl.Builder().let {
                val defaultAllocator = DefaultAllocator(true, ExoConstants.SIXTY_FOUR_KB,0)// not sure about this, but it has the same specs as default
                it.setAllocator(defaultAllocator)
                it.setBackBuffer(ExoConstants.FIFTY_SECONDS,true)
                it.setBufferDurationsMs(
                    ExoConstants.TWO_MINS,
                    ExoConstants.TEN_MINS,
                    ExoConstants.ONE_SECOND,
                    0
                )
                it.setTargetBufferBytes(DefaultLoadControl.DEFAULT_TARGET_BUFFER_BYTES)
                it.setPrioritizeTimeOverSizeThresholds(false)
                it.build()
            }
        }
    }
}

