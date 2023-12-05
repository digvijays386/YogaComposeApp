package com.digvijay.yogaadminapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digvijay.yogaadminapp.data.local.dao.TeacherDao
import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity
import com.digvijay.yogaadminapp.utills.TeacherEvent
import com.digvijay.yogaadminapp.utills.TeacherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TeacherViewModel (
    private val dao: TeacherDao
) : ViewModel() {

    private val _state = MutableStateFlow(TeacherState())
    private val _teachers = dao.getAllTeachers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val state = combine(_state, _teachers) { state, teachers ->
        state.copy(teachers = teachers)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TeacherState()
    )

    fun onEvent(event: TeacherEvent) {
        when(event) {
            is TeacherEvent.Save -> {
                val name = state.value.name
                if (name.isBlank()) return
                val course = TeacherEntity(name = name)
                viewModelScope.launch {
                    dao.insertTeacher(course)
                }
                _state.update { it.copy(isAddingTeacher = false, name = "false") }
            }

            TeacherEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingTeacher = false
                ) }
            }
            is TeacherEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
            TeacherEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingTeacher = true
                ) }
            }
        }
    }
}