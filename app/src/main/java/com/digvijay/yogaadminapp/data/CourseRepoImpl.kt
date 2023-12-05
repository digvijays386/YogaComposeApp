package com.digvijay.yogaadminapp.data

import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepoImpl @Inject constructor(
    private val dao: CourseDao,
    private val apiService: ApiService) : CourseRepo{
    override suspend fun uploadCourses(courses: List<CourseEntity>) = apiService.uploadCourses(courses)
    override fun getAllCourses(): Flow<List<CourseEntity>> {
        return dao.getAllCourses()
    }

    override suspend fun getCourseById(id: Int): CourseEntity {
        return dao.getCourseById(id)
    }

    override suspend fun insertCourse(courseEntity: CourseEntity) {
        dao.insertCourse(courseEntity)
    }

    override suspend fun deleteCourse(courseEntity: CourseEntity) {
        dao.deleteCourse(courseEntity)
    }

    override suspend fun updateCourse(courseEntity: CourseEntity) {
        dao.updateCourse(courseEntity)
    }

}