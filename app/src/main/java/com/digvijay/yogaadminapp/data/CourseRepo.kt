package com.digvijay.yogaadminapp.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.remote.UploadResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CourseRepo {

    suspend fun uploadCourses(courses: List<CourseEntity>): Response<UploadResponse>

    fun getAllCourses(): Flow<List<CourseEntity>>

    suspend fun getCourseById(id: Int): CourseEntity

    suspend fun insertCourse(courseEntity: CourseEntity)

    suspend fun deleteCourse(courseEntity: CourseEntity)
    suspend fun updateCourse(courseEntity: CourseEntity)

}