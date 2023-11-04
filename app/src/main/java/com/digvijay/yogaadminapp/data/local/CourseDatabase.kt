package com.digvijay.yogaadminapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity

@Database(
    version = 1,
    entities = [CourseEntity::class]
)
abstract class CourseDatabase: RoomDatabase() {
    abstract val dao: CourseDao

    companion object {
        const val name = "yoga_db"
    }
}