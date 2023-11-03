package com.digvijay.yogaadminapp.domain.model

data class Course(
    val id: Int? = null,
    val dayOfWeek: String = "",
    val time: String = "",
    val duration: Int = 1,
    val price: Int = 0,
    val capacity: Int = 1,
    val desc: String = "",
    val type: String = "",
    val instruction: String? = "",
    val openingHour: Int? = 0
)