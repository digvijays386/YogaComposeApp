package com.digvijay.yogaadminapp.ui.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState

@Composable
fun CommonDialog(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { onEvent(CourseEvent.HideAlert) },
        title = { Text(text = "Are you sure?") },
        text = {
            Text(text = "You can't retrieve deleted data later. \nPlease check again if you really want to delete.")
        },
        confirmButton = {
            Button(onClick = {
                onEvent(CourseEvent.DeleteCourse(state.courses[state.index]))
                onEvent(CourseEvent.HideAlert)
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(onClick = { onEvent(CourseEvent.HideAlert) }) {
                Text(text = "No")
            }

        }
    )

}