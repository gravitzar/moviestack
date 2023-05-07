package com.zerocoders.moviestack

import android.app.Application
import com.zerocoders.moviestack.utils.DownloaderImpl
import com.zerocoders.moviestack.widgets.player.VideoPlayerCacheManager
import dagger.hilt.android.HiltAndroidApp
import org.schabi.newpipe.extractor.NewPipe

@HiltAndroidApp
class MovieStackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewPipe.init(DownloaderImpl.getInstance())
        VideoPlayerCacheManager.initialize(this, 2147483648) // 2GB
    }
}