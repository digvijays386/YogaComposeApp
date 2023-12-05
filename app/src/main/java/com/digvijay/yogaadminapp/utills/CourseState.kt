package com.digvijay.yogaadminapp.utills

import com.digvijay.yogaadminapp.data.local.entity.CourseEntity

data class CourseState(
    val courses: List<CourseEntity> = emptyList(),
    val dayOfWeek: String = "",
    val date: String = "",
    val time: String = "",
    val type: String = "",
    val duration: String = "",
    val desc: String = "",
    val capacity: Int = 1,
    val price: Int = 1,
    val isAddingCourse: Boolean = false,
    val shouldShowAlert: Boolean = false,
    val index: Int = 0,
    val uploading: Int = 0
)