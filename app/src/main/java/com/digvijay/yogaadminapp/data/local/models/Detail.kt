package com.digvijay.yogaadminapp.data.local.models

data class Detail(
    val classList: List<Studio>,
    val dayOfWeek: String,
    val timeOfDay: String
)

data class Studio(
    val date: String,
    val teacher: String
)

data class UploadReq(
    val detailList: List<Detail>,
    val userId: String
)