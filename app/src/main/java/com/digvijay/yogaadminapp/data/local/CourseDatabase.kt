package com.digvijay.yogaadminapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.digvijay.yogaadminapp.data.local.dao.ClassInstanceDao
import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.local.dao.TeacherDao
import com.digvijay.yogaadminapp.data.local.entity.ClassInstanceEntity
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity

@Database(
    version = 1,
    entities = [CourseEntity::class, ClassInstanceEntity::class, TeacherEntity::class]
)
abstract class CourseDatabase: RoomDatabase() {
    abstract val dao: CourseDao
    abstract val classInstanceDao: ClassInstanceDao
    abstract val teacherDao: TeacherDao

    companion object {
        const val name = "yoga_db"
    }
}