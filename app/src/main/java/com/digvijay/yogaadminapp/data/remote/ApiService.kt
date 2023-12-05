package com.digvijay.yogaadminapp.data.remote

import com.digvijay.yogaadminapp.data.local.models.UploadReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("SubmitClasses")
    suspend fun uploadCourses(@Body courses: UploadReq): Response<UploadResponse>
}