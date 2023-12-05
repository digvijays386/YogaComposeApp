package com.digvijay.yogaadminapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.digvijay.yogaadminapp.data.local.entity.ClassInstanceEntity
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity
import com.digvijay.yogaadminapp.data.local.entity.TeacherEntity
import com.digvijay.yogaadminapp.utills.ScheduleEvent
import com.digvijay.yogaadminapp.utills.ScheduleState

@Composable
fun ScheduleScreen(
    state: ScheduleState,
    courses: List<CourseEntity>,
    teachers: List<TeacherEntity>,
    onEvent: (ScheduleEvent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schedules") },
                backgroundColor = Color.White,
                elevation = 1.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ScheduleEvent.ShowDialog) },
                modifier = Modifier.padding(bottom = 48.dp),
                content = { Icon(Icons.Default.Add, contentDescription = "Add class") }
            )
        }
    ) {
        if (state.isAddingClass) {
            AddClassInstances(state = state, courses = courses, teachers = teachers,onEvent = onEvent)
        }

        LazyColumn {
            itemsIndexed(state.classes) {index, schedule ->
                ScheduleTile(schedule)
            }
        }
    }
}

@Composable
fun ScheduleTile(schedule: ClassInstanceEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = schedule.date, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = schedule.teacher, style = MaterialTheme.typography.h5)
            schedule.comments?.let {
                if (it.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = it, style = MaterialTheme.typography.h6)
                }
            }
        }
    }
}
