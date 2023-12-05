package com.digvijay.yogaadminapp.utills

import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity

data class TeacherState(
    val teachers: List<TeacherEntity> = emptyList(),
    val name: String = "",
    val isAddingTeacher: Boolean = false,
)
