package com.zerocoders.moviestack

import android.app.Application
import com.zerocoders.moviestack.utils.DownloaderImpl
import dagger.hilt.android.HiltAndroidApp
import org.schabi.newpipe.extractor.NewPipe

@HiltAndroidApp
class MovieStackApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        NewPipe.init(DownloaderImpl.getInstance())
    }
}