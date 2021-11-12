package work.curioustools.third_party_exoplayer

import android.content.Context
import com.google.android.exoplayer2.BuildConfig
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.LoadControl
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.PriorityTaskManager
import com.google.common.base.Predicate
import work.curioustools.third_party_exoplayer.exo_cache.ExoMyCacheKeyGenerator


fun defaultAgent(): String {
    val ctx: Context? = null
    return ctx.agent()
}

fun getHttpDSF(
    allowCPRedirects: Boolean? = true,
    connectTimeout: Int? = DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
    connectTimePredicate: Predicate<String>? = null,
    defaultReqProperties: Map<String, String>? = null,
    keepPostFor302: Boolean? = null,
    readTimeout: Int? = DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
    transferListener: TransferListener? = null,
    userAgent: String? = defaultAgent(),
): HttpDataSource.Factory {
    // an http data source factory is like a component of a big pipe which works on the network layer, for fetching of data packets from the server to user device renderer view
    val factory = DefaultHttpDataSource.Factory().also { f ->
        allowCPRedirects?.let { f.setAllowCrossProtocolRedirects(it) }
        connectTimeout?.let { f.setConnectTimeoutMs(it) }
        connectTimePredicate?.let { f.setContentTypePredicate(it) }
        defaultReqProperties?.let { f.setDefaultRequestProperties(it) }
        keepPostFor302?.let { f.setKeepPostFor302Redirects(it) }
        readTimeout?.let { f.setReadTimeoutMs(it) }
        transferListener?.let { f.setTransferListener(it) }
        userAgent?.let { f.setUserAgent(it) }
    }
    return factory
}


fun getDefaultDSF(
    ctx: Context,
    httpDSF: HttpDataSource.Factory,
    listener: TransferListener? = null,
): DefaultDataSourceFactory {
    // a default data source factory is like a generic pipe that can fetch data packets from any layer : from server to  renederer view, from files to renderer view , from some memory block to renderer view, etc. it is best to use as it is completely customizable, meaning we can pass our own http data source, file data source , and it will also implemt several optimisations.
    return DefaultDataSourceFactory(ctx, listener, httpDSF)
}


fun getCacheDSF(
    cache: SimpleCache,
    cacheKeyGenerator: ExoMyCacheKeyGenerator,
    cacheEventListener: CacheDataSource.EventListener? = null,
    cacheReadDSF: DataSource.Factory? = null,
    cacheWriteDSKF: DataSink.Factory? = null,
    flags: Int? = null,
    upstreamDSF: DataSource.Factory? = null,
    priority: Int? = null,
    priorityManager: PriorityTaskManager? = null,
): CacheDataSource.Factory {
    // Build data source factory with cache enabled, if data is available in cache it will return immediately and use cache, otherwise it will open a new connection to get the data.
    // note: the cache must be a singleton across the app, thus its best to initialise it in application subclass
    val factory = CacheDataSource.Factory().also { f ->
        cache.let { f.setCache(it) }
        cacheKeyGenerator.let { f.cacheKeyFactory = it }
        cacheReadDSF?.let { f.setCacheReadDataSourceFactory(it) }
        cacheWriteDSKF?.let { f.setCacheWriteDataSinkFactory(it) }
        cacheEventListener?.let { f.setEventListener(it) }
        flags?.let { f.setFlags(it) }
        upstreamDSF?.let { f.setUpstreamDataSourceFactory(it) }

        priority?.let { f.setUpstreamPriority(it) }

        priorityManager?.let { f.upstreamPriorityTaskManager = it }

    }
    return factory
}


fun getDefaultAllocator(
    trimOnReset: Boolean = true,
    individualAllocationSize: Int = ExoConstants.SIXTY_FOUR_KB,
    initialAllocationCount: Int = ExoConstants.ZERO,
): DefaultAllocator {
    // not sure about this, but it has the same specs as default
    return DefaultAllocator(trimOnReset, individualAllocationSize, initialAllocationCount)
}


fun getPreFetchLoadControlBuilder(
    allocator: DefaultAllocator = getDefaultAllocator(),
    backBuffer: Pair<Int, Boolean> = ExoConstants.FIFTY_SECONDS to true,
    bufferMinMillis: Int = ExoConstants.TWO_MINS,
    bufferMaxMillis: Int = ExoConstants.TEN_MINS,
    bufferPlaybackMinMs: Int = ExoConstants.ONE_SECOND,
    bufferReBufferMs: Int = ExoConstants.ZERO,
    prioritiseTimeOverThreshold: Boolean = false,
    targetBufferSize: Int = DefaultLoadControl.DEFAULT_TARGET_BUFFER_BYTES,

    ): DefaultLoadControl.Builder {

    // this is a useful object to customise the properties of an exoplayer's abstract player.. it determines hw much bufferring will be done for forward/backward  press. my app has large(>1gb) videos, so i think bufferring for 5 minutes is optimal, but for other apps, default options are correct too.
    return DefaultLoadControl.Builder().also {
        it.setAllocator(allocator)
        it.setBackBuffer(backBuffer.first, backBuffer.second)
        it.setBufferDurationsMs(bufferMinMillis, bufferMaxMillis, bufferPlaybackMinMs, bufferReBufferMs)
        it.setPrioritizeTimeOverSizeThresholds(prioritiseTimeOverThreshold)
        it.setTargetBufferBytes(targetBufferSize)

    }
}

fun getPreFetchLoadControl(builder: DefaultLoadControl.Builder = getPreFetchLoadControlBuilder()): DefaultLoadControl {
    return builder.build()
}



fun getProgressiveMSF(
    dsf: DataSource.Factory,
    cacheKeyGenerator: ExoMyCacheKeyGenerator?,
    checkIntervalBytes: Int? = null,
    errorHandlingPolicy: LoadErrorHandlingPolicy? = null,
    drmSessionManagerProvider: DrmSessionManagerProvider? = null,
): ProgressiveMediaSource.Factory {
    //MSF : MediaSource.Factory
    return ProgressiveMediaSource.Factory(dsf).also { f ->
        drmSessionManagerProvider?.let { f.setDrmSessionManagerProvider(it) }
        errorHandlingPolicy?.let { f.setLoadErrorHandlingPolicy(it) }
        checkIntervalBytes?.let { f.setContinueLoadingCheckIntervalBytes(it) }
        //f.setDrmUserAgent() //f.setDrmSessionManager() //f.setDrmHttpDataSourceFactory() //f.setCustomCacheKey() //f.setStreamKeys()
    }
}

fun getProgressiveMS(item: MediaItem, pmsf: ProgressiveMediaSource.Factory): ProgressiveMediaSource {
    return pmsf.createMediaSource(item)
}

fun getDefaultMediaItemBuilder(cacheKey: String? = null): MediaItem.Builder {
    return MediaItem.Builder().also { builder ->
        cacheKey?.let { builder.setCustomCacheKey(it) }
        //todo : add mor ebased on requirement
    }
}
