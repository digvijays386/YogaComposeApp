package com.digvijay.yogaadminapp.utills

sealed interface TeacherEvent{
    object ShowDialog: TeacherEvent
    object HideDialog: TeacherEvent
    data object Save: TeacherEvent
    data class SetName(val name: String) : TeacherEvent
}