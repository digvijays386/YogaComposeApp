package com.digvijay.yogaadminapp.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity
import com.digvijay.yogaadminapp.utills.CourseDropdown
import com.digvijay.yogaadminapp.utills.ScheduleEvent
import com.digvijay.yogaadminapp.utills.ScheduleState
import com.digvijay.yogaadminapp.utills.TeacherDropdown

@Composable
fun AddClassInstances(
    state: ScheduleState,
    courses: List<CourseEntity>,
    teachers: List<TeacherEntity>,
    onEvent: (ScheduleEvent) -> Unit,
) {

    AlertDialog(onDismissRequest = { onEvent(ScheduleEvent.HideDialog) },
        title = { Text(text = "Fill Class Instance Detail") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                CourseDropdown(
                    courses = courses,
                    selectedDate = state.date,
                    onDateSelected = { id, date ->
                        onEvent(ScheduleEvent.SetCourseId(id))
                        onEvent(ScheduleEvent.SetDate(date)) }
                )
                TeacherDropdown(
                    teachers = teachers.map { it.name },
                    selectedTeacher = state.teacher,
                    onTeacherSelected = {
                        onEvent(ScheduleEvent.SetTeacher(it))
                    }
                )
                OutlinedTextField(
                    value = state.comments,
                    onValueChange = {
                        onEvent(ScheduleEvent.SetComment(it))
                    },
                    placeholder = { Text(text = "Any Comments") },
                    label = { Text(text = "Comments") },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }
        },
        confirmButton = {
            Button(onClick = { onEvent(ScheduleEvent.Save) }) {
                Text(text = "Save Class Instance")
            }
        }
    )
}