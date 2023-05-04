package com.example.unsplash.di

import android.app.Application
import androidx.room.Room
import com.example.unsplash.data.local.ImageDatabase
import com.example.unsplash.data.remote.UnsplashApi
import com.example.unsplash.data.repository.AccessKeyRepositoryImpl
import com.example.unsplash.data.repository.UnsplashImageRepositoryImpl
import com.example.unsplash.domain.repository.AccessKeyRepository
import com.example.unsplash.domain.repository.UnsplashImageRepository
import com.example.unsplash.utils.AppDispatcherProvider
import com.example.unsplash.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    // Provides an instance of UnsplashApi using Retrofit.
    @Provides
    @Singleton
    fun provideUnsplashApi(): UnsplashApi {
        return Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApi::class.java)
    }

    // Provides an instance of UnsplashImageRepositoryImpl which requires UnsplashApi and ImageDatabase.
    @Provides
    @Singleton
    fun provideImageRepository(api: UnsplashApi, db: ImageDatabase): UnsplashImageRepository {
        return UnsplashImageRepositoryImpl(api, db.getDao())
    }

    // Provides an instance of AccessKeyRepositoryImpl.
    @Provides
    @Singleton
    fun provideKeyRepository(): AccessKeyRepository {
        return AccessKeyRepositoryImpl()
    }

    // Provides an instance of ImageDatabase using Room.
    @Provides
    @Singleton
    fun provideDatabase(app: Application): ImageDatabase {
        return Room
            .databaseBuilder(app, ImageDatabase::class.java, "img_db")
            .build()
    }

    // Provides an instance of AppDispatcherProvider which implements DispatcherProvider.
    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return AppDispatcherProvider()
    }
}
