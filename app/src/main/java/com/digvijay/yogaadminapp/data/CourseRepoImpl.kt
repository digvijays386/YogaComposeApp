package com.digvijay.yogaadminapp.data

import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.local.models.Detail
import com.digvijay.yogaadminapp.data.local.models.Studio
import com.digvijay.yogaadminapp.data.local.models.UploadReq
import com.digvijay.yogaadminapp.data.remote.ApiService
import com.digvijay.yogaadminapp.data.remote.UploadResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CourseRepoImpl @Inject constructor(
    private val dao: CourseDao,
    private val apiService: ApiService) : CourseRepo{
    override suspend fun uploadCourses(courses: List<CourseEntity>) : Response<UploadResponse> {


     return apiService.uploadCourses(makerequest())
    }

    private fun makerequest(): UploadReq {
        val detail1 = Detail(
            listOf(
                Studio("24/10/2023", "Russell"),
                Studio("31/10/2023", "Joe")
            ),
            "Tuesday",
            "18:00"
        )

        val detail2 = Detail(
            listOf(
                Studio("26/10/2023", "Elena"),
                Studio("02/11/2023", "Katya")
            ),
            "Thursday",
            "07:00"
        )

        return UploadReq(
            listOf(detail1, detail2),
            "wm123"
        )
    }

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