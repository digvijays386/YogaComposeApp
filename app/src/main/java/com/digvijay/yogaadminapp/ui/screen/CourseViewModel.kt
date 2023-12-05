package com.digvijay.yogaadminapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digvijay.yogaadminapp.data.CourseRepo
import com.digvijay.yogaadminapp.data.local.dao.CourseDao
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseViewModel @Inject constructor(
    private val repo: CourseRepo
): ViewModel() {

    private val _state = MutableStateFlow(CourseState())
    private val _courses = repo.getAllCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val state = combine(_state, _courses) { state, courses ->
        state.copy(courses = courses)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CourseState()
    )

    fun onEvent(event: CourseEvent) {
        when(event) {
            is CourseEvent.DeleteCourse -> {
                viewModelScope.launch {
                    repo.deleteCourse(event.courseEntity)
                }
            }
            CourseEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingCourse = false
                ) }
            }
            CourseEvent.SaveCourse -> {
                val dayOfWeek = state.value.dayOfWeek
                val time = state.value.time
                val duration = state.value.duration
                val capacity = state.value.capacity

                if (dayOfWeek.isBlank() || time.isBlank() || duration.isBlank() || capacity == 1) {
                    return
                }
                val course = CourseEntity(dayOfWeek = dayOfWeek, time = time, duration = duration, capacity = capacity)

                viewModelScope.launch {
                    repo.insertCourse(course)
                }
                _state.update {
                    it.copy(
                        isAddingCourse = false,
                        time = "",
                        capacity = 1,
                        dayOfWeek = "",
                        duration = ""
                    )
                }
            }
            is CourseEvent.SetCapacity -> {
                _state.update { it.copy(capacity = event.capacity) }
            }
            is CourseEvent.UpdateIndex -> {
                _state.update { it.copy(index = event.index) }
            }
            is CourseEvent.SetDayOfWeek -> {
                _state.update { it.copy(dayOfWeek = event.dayOfWeek) }
            }
            is CourseEvent.SetDuration -> {
                _state.update { it.copy(duration = event.duration) }
            }
            is CourseEvent.SetTime -> {
                _state.update { it.copy(time = event.time) }
            }
            CourseEvent.ShowDialog -> {
                _state.update { it.copy(isAddingCourse = true) }
            }
            CourseEvent.ShowAlert -> {
                _state.update { it.copy(shouldShowAlert = true) }
            }
            CourseEvent.HideAlert -> {
                _state.update { it.copy(shouldShowAlert = false) }
            }
            CourseEvent.UploadToCloud -> {
                viewModelScope.launch {
                    try {
                        val response = repo.uploadCourses(state.value.courses)
                        response.body()?.let {
                            if (it.uploadResponseCode == "SUCCESS")
                                _state.update { it.copy(uploading = 1) }
                            else
                                _state.update { it.copy(uploading = 2) }
                        }
                    } catch (e: Exception) {
                        _state.update { it.copy(uploading = 3) }
                    }
                }
            }
        }
    }
}