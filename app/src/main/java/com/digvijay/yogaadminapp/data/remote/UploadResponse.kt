package com.digvijay.yogaadminapp.data.remote

data class UploadResponse(
    val uploadResponseCode: String,
    val userId: String,
    val number: Int,
    val courses: String,
    val message: String
)