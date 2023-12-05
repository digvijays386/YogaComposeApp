package com.digvijay.yogaadminapp.data.di

import android.content.Context
import androidx.room.Room
import com.digvijay.yogaadminapp.data.CourseRepo
import com.digvijay.yogaadminapp.data.CourseRepoImpl
import com.digvijay.yogaadminapp.data.local.CourseDatabase
import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideCourseDao(courseDatabase: CourseDatabase): CourseDao {
        return courseDatabase.dao
    }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideCourseRepository(dao: CourseDao, apiService: ApiService): CourseRepo {
        return CourseRepoImpl(dao, apiService)
    }
}