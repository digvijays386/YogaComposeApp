package com.digvijay.yogaadminapp.utills


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.digvijay.yogaadminapp.data.local.entity.CourseEntity

@Composable
fun CourseDropdown(
    courses: List<CourseEntity>,
    selectedDate: String,
    onDateSelected: (Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Course Date", style = TextStyle(fontWeight = FontWeight.Bold))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
        ) {
            Text(
                text = selectedDate.ifEmpty { "Select Course" },
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            courses.forEach { course ->
                DropdownMenuItem(onClick = {
                    course.id?.let { onDateSelected(it, course.date) }
                    expanded = false
                }) {
                    Text(text = course.dayOfWeek)
                }
            }
        }
    }
}
