package com.digvijay.yogaadminapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM CourseEntity")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM CourseEntity WHERE id = :id")
    suspend fun getCourseById(id: Int): CourseEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(courseEntity: CourseEntity)

    @Delete
    suspend fun deleteCourse(courseEntity: CourseEntity)

    @Update
    suspend fun updateCourse(courseEntity: CourseEntity)
}