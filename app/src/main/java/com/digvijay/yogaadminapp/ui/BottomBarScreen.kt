package com.digvijay.yogaadminapp.ui

import com.digvijay.yogaadminapp.R

sealed class BottomBarScreen(
    var title: String,
    var icon: Int,
    var route: String
) {
    object Courses : BottomBarScreen("Courses", R.drawable.ic_courses, "courses")
    object Schedule : BottomBarScreen("Schedule", R.drawable.ic_schedule, "schedule")
    object Teacher : BottomBarScreen("Teacher", R.drawable.ic_teacher, "teacher")
}