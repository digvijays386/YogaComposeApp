package com.digvijay.yogaadminapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        title = { "Add Yoga Course" },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = state.dayOfWeek,
                    onValueChange = {
                        onEvent(CourseEvent.SetDayOfWeek(it))
                    }, placeholder = { Text(text = "Day of week") }
                )
                TextField(
                    value = state.time,
                    onValueChange = {
                        onEvent(CourseEvent.SetTime(it))
                    }, placeholder = { Text(text = "Time") }
                )
                TextField(
                    value = state.duration,
                    onValueChange = {
                        onEvent(CourseEvent.SetDuration(it))
                    }, placeholder = { Text(text = "Duration") }
                )
                TextField(
                    value = state.capacity.toString(),
                    onValueChange = {
                        onEvent(CourseEvent.SetCapacity(it.toInt()))
                    }, placeholder = { Text(text = "Capacity") }
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