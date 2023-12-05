package com.digvijay.yogaadminapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digvijay.yogaadminapp.data.local.dao.ClassInstanceDao
import com.digvijay.yogaadminapp.data.local.entity.ClassInstanceEntity
import com.digvijay.yogaadminapp.utills.ScheduleEvent
import com.digvijay.yogaadminapp.utills.ScheduleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleViewModel (
    private val dao: ClassInstanceDao
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    private val _classes = dao.getAllSchedules()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val state = combine(_state, _classes) { state, classes ->
        state.copy(classes = classes)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleState()
    )

    fun onEvent(event: ScheduleEvent) {
        when(event) {
            is ScheduleEvent.Save -> {
                val date = state.value.date
                val teacher = state.value.teacher
                val comment = state.value.comments
                val courseId = state.value.courseId
                if (date.isBlank() || teacher.isBlank()) return
                val course = ClassInstanceEntity(
                    courseId = courseId,
                    date = date,
                    teacher = teacher,
                    comments = comment
                )
                viewModelScope.launch {
                    dao.insertClassInstance(course)
                }
                _state.update { it.copy(isAddingClass = false, date = "", teacher = "", courseId = 1) }
            }

            ScheduleEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingClass = false
                ) }
            }
            is ScheduleEvent.SetTeacher -> {
                _state.update { it.copy(teacher = event.name) }
            }
            ScheduleEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingClass = true
                ) }
            }

            is ScheduleEvent.SetComment -> {
                _state.update { it.copy(comments = event.name) }
            }

            is ScheduleEvent.SetDate -> {
                _state.update { it.copy(date = event.name) }
            }

            is ScheduleEvent.SetCourseId -> {
                _state.update { it.copy(courseId = event.id) }
            }
        }
    }
}