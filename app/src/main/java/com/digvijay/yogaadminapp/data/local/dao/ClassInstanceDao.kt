package com.digvijay.yogaadminapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.digvijay.yogaadminapp.data.local.entity.ClassInstanceEntity
import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassInstanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClassInstance(classInstance: ClassInstanceEntity)

    @Query("SELECT * FROM ClassInstanceEntity WHERE courseId = :courseId")
    suspend fun getClassInstancesForCourse(courseId: Long): List<ClassInstanceEntity>

    @Query("SELECT * FROM ClassInstanceEntity")
    fun getAllSchedules(): Flow<List<ClassInstanceEntity>>
}