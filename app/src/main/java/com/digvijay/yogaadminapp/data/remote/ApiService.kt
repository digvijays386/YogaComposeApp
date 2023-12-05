package com.digvijay.yogaadminapp.data.remote

import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("comp1424cw")
    suspend fun uploadCourses(@Body courses: List<CourseEntity>): Response<Unit>
}