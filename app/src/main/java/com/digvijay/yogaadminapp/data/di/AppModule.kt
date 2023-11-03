package com.digvijay.yogaadminapp.data.di

import android.content.Context
import androidx.room.Room
import com.digvijay.yogaadminapp.data.local.CourseDatabase
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
    fun provideCourseDatabase(@ApplicationContext context: Context): CourseDatabase =
        Room.databaseBuilder(
            context,
            CourseDatabase::class.java,
            CourseDatabase.name
        ).build()

}