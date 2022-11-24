package com.mahmoudhamdyae.mynotes.di

import com.mahmoudhamdyae.mynotes.database.FirebaseApi
import com.mahmoudhamdyae.mynotes.database.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseApi(): FirebaseApi {
        return FirebaseApi()
    }

    @Provides
    @Singleton
    fun provideRepository(firebaseApi: FirebaseApi): Repository {
        return Repository(firebaseApi)
    }
}