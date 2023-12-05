package com.digvijay.yogaadminapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit
) {

    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(CourseEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add course")
            }
        }
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
            Row {
                Text(
                    text = "Yoga Courses", fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { onEvent(CourseEvent.UploadToCloud) }) {
                    Text("Upload Courses")
                }
            }
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
                                Text(text = "${course.dayOfWeek}", fontSize = 20.sp)
                                Text(text = "${course.duration} hour", fontSize = 16.sp)
                                Text(text = "${course.time} : 00", fontSize = 16.sp)
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