package com.digvijay.yogaadminapp.utills

import com.digvijay.yogaadminapp.data.local.entity.CourseEntity

data class CourseState(
    val courses: List<CourseEntity> = emptyList(),
    val dayOfWeek: String = "",
    val time: String = "",
    val duration: String = "",
    val capacity: Int = 1,
    val isAddingCourse: Boolean = false,
    val shouldShowAlert: Boolean = false,
    val index: Int = 0,
    val uploading: Int = 0
)