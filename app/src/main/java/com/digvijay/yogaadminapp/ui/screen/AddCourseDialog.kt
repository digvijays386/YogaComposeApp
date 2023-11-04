package com.digvijay.yogaadminapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseDialog(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { onEvent(CourseEvent.HideDialog) },
        title = { Text(text = "Add Yoga Course Detail") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.dayOfWeek,
                    onValueChange = {
                        onEvent(CourseEvent.SetDayOfWeek(it))
                    },
                    placeholder = { Text(text = "Day of week") },
                    label = { Text(text = "Day of week") },
                    isError = state.dayOfWeek.isBlank(),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
                OutlinedTextField(
                    value = state.time,
                    onValueChange = {
                        onEvent(CourseEvent.SetTime(it))
                    },
                    placeholder = { Text(text = "Time") },
                    label = { Text(text = "Time") },
                    isError = state.time.isBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = state.duration,
                    onValueChange = {
                        onEvent(CourseEvent.SetDuration(it))
                    },
                    placeholder = { Text(text = "Duration") },
                    label = { Text(text = "Duration") },
                    isError = state.duration.isBlank(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = state.capacity.toString(),
                    onValueChange = {
                        onEvent(CourseEvent.SetCapacity(if (it.isBlank()) 1 else it.toInt()))
                    },
                    placeholder = { Text(text = "Capacity") },
                    label = { Text(text = "Capacity") },
                    isError = state.capacity == 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = { onEvent(CourseEvent.SaveCourse) }) {
                Text(text = "Save Course")
            }
        }
    )
    
}