package com.zerocoders.moviestack.widgets.player

import android.content.Context
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object VideoPlayerCacheManager {

    private lateinit var cacheInstance: Cache

    fun initialize(context: Context, maxCacheBytes: Long) {
        if (::cacheInstance.isInitialized) {
            return
        }

        cacheInstance = SimpleCache(
            File(context.cacheDir, "movie_stack_video"),
            LeastRecentlyUsedCacheEvictor(maxCacheBytes),
            StandaloneDatabaseProvider(context),
        )
    }

    internal fun getCache(): Cache? =
        if (::cacheInstance.isInitialized) {
            cacheInstance
        } else {
            null
        }
}
