package com.digvijay.yogaadminapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState

@Composable
fun HomeScreen(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit
) {

    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yoga Courses") },
                backgroundColor = Color.White,
                elevation = 1.dp,
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Upload to Cloud").forEach { item ->
                            DropdownMenuItem(onClick = {
                                expanded = false
                                onEvent(CourseEvent.UploadToCloud)
                            }) {
                                Text(item)
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(CourseEvent.ShowDialog) },
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add course")
            }
        },
        isFloatingActionButtonDocked = true
    ) { padding ->
        if (state.isAddingCourse) {
            AddCourseDialog(state = state, onEvent = onEvent)
        }
        if (state.shouldShowAlert) {
            CommonDialog(state = state, onEvent = onEvent)
        }
        if (state.uploading == 1){
            Toast.makeText(context, "Upload Success", Toast.LENGTH_LONG).show()
        }
        if (state.uploading == 2) {
            Toast.makeText(context, "Upload Failed", Toast.LENGTH_LONG).show()
        }
        if (state.uploading == 3) {
            Toast.makeText(context, "APi error", Toast.LENGTH_LONG).show()
        }
        Column {

            if (state.courses.isEmpty()) {
                Text(
                    text = "No course added yet", fontSize = 22.sp,
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(state.courses) { index, course ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                ) {
                                    Row {
                                        Text(text = "${course.dayOfWeek}", fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(30.dp))
                                        Text(text = "Time: ${course.time}", fontSize = 16.sp)
                                    }
                                    Row {
                                        Text(text = "Price: ${course.price}", fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(40.dp))
                                        Text(
                                            text = "Capacity: ${course.capacity}",
                                            fontSize = 16.sp
                                        )
                                    }
                                    Row {
                                        Text(
                                            text = "Duration: ${course.duration}",
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(36.dp))
                                        Text(text = "Type: ${course.type}", fontSize = 16.sp)
                                    }
                                    if (course.desc.isNotEmpty()) {
                                        Text(text = "Description: ${course.desc}")
                                    }
                                }
                                IconButton(onClick = {
                                    onEvent(CourseEvent.UpdateIndex(index))
                                    onEvent(CourseEvent.ShowAlert)
                                })
                                {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "delete course"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}