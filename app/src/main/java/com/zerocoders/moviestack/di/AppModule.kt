package com.zerocoders.moviestack.di

import android.content.Context
import com.zerocoders.moviestack.SessionPreferenceManager
import com.zerocoders.moviestack.network.KtorOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient() = KtorOkHttpClient.getHttpClient()

    @Singleton
    @Provides
    fun provideSessionPreferenceManager(@ApplicationContext context: Context): SessionPreferenceManager {
        return SessionPreferenceManager(context)
    }
}