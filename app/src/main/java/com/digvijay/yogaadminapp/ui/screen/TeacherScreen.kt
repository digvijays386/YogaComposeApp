package com.digvijay.yogaadminapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.digvijay.yogaadminapp.utills.TeacherEvent
import com.digvijay.yogaadminapp.utills.TeacherState

@Composable
fun TeacherScreen(
    state: TeacherState,
    onEvent: (TeacherEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teachers") },
                backgroundColor = Color.White,
                elevation = 1.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TeacherEvent.ShowDialog) },
                modifier = Modifier.padding(bottom = 48.dp),
                content = { Icon(Icons.Default.Add, contentDescription = "add teacher") }
            )
        },
    ) {
        if (state.isAddingTeacher) {
            AddTeacherDialog(state = state, onEvent = onEvent)
        }

        LazyColumn {
            itemsIndexed(state.teachers) { index, teacher ->
                TeacherTile(teacher.name)
            }
        }
    }
}

@Composable
fun TeacherTile(teacher: String) {
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
            Text(text = teacher, style = typography.h4)
        }
    }
}
