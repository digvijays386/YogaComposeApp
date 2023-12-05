package com.digvijay.yogaadminapp.utills

sealed interface ScheduleEvent{
    object ShowDialog: ScheduleEvent
    object HideDialog: ScheduleEvent
    data object Save: ScheduleEvent
    data class SetDate(val name: String) : ScheduleEvent
    data class SetCourseId(val id: Int) : ScheduleEvent
    data class SetTeacher(val name: String) : ScheduleEvent
    data class SetComment(val name: String) : ScheduleEvent

}