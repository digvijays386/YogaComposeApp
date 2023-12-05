package com.digvijay.yogaadminapp.utills

import com.digvijay.yogaadminapp.data.local.entity.CourseEntity

sealed interface CourseEvent{
    object SaveCourse: CourseEvent
    data class SetDayOfWeek(val dayOfWeek: String) : CourseEvent
    data class SetDate(val date: String) : CourseEvent
    data class SetTime(val time: String): CourseEvent
    data class SetDuration(val duration: String): CourseEvent
    data class SetType(val name: String): CourseEvent
    data class SetDesc(val name: String): CourseEvent
    data class SetCapacity(val capacity: Int): CourseEvent
    data class SetPrice(val price: Int): CourseEvent
    data class AddClassInstance(val id: Int):CourseEvent
    object ShowDialog: CourseEvent
    object HideDialog: CourseEvent
    data class DeleteCourse(val courseEntity: CourseEntity): CourseEvent
    object ShowAlert: CourseEvent
    object HideAlert: CourseEvent
    data class UpdateIndex(val index: Int): CourseEvent
    data object UploadToCloud: CourseEvent
    data object OpenCourse: CourseEvent
}