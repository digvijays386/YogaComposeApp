package com.digvijay.yogaadminapp.utills


import com.digvijay.yogaadminapp.data.local.entity.ClassInstanceEntity

data class ScheduleState(
    val classes: List<ClassInstanceEntity> = emptyList(),
    val date: String = "",
    val teacher: String = "",
    val courseId: Int = 1,
    val comments: String = "",
    val isAddingClass: Boolean = false,
)
