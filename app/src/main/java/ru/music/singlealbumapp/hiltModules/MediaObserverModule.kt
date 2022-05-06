package ru.music.singlealbumapp.hiltModules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.music.singlealbumapp.media.MediaLifecycleObserver
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MediaObserverModule {
    @Provides
    @Singleton
    fun provideMediaObserver() = MediaLifecycleObserver()
}