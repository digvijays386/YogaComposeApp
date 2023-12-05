package com.digvijay.yogaadminapp.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.digvijay.yogaadminapp.utills.CourseEvent
import com.digvijay.yogaadminapp.utills.CourseState
import com.digvijay.yogaadminapp.utills.TypeDropdown
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCourseDialog(
    state: CourseState,
    onEvent: (CourseEvent) -> Unit
) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    var selectedDateText by remember { mutableStateOf("$dayOfMonth-${month + 1}-$year") }

    // Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    var selectedTimeText by remember { mutableStateOf("") }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth-${selectedMonth + 1}-$selectedYear"
        }, year, month, dayOfMonth
    )

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, true
    )

    val dateFormatter = DateTimeFormatter.ofPattern("d-M-yyyy")
    val date = LocalDate.parse(selectedDateText, dateFormatter)
    val dayOfWeek = date.dayOfWeek

    AlertDialog(onDismissRequest = { onEvent(CourseEvent.HideDialog) },
        title = { Text(text = "Add Yoga Course Detail") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.clickable { datePicker.show() }) {
                    Text(
                        text = "Day of week:  ",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (dayOfWeek.name.isNotEmpty()) {
                            onEvent(CourseEvent.SetDayOfWeek(dayOfWeek.name))
                            onEvent(CourseEvent.SetDate(selectedDateText))
                            "${dayOfWeek.name}"
                        } else {
                            "Day of week"
                        },
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.clickable { timePicker.show() }) {
                    Text(
                        text = "Pick a time:  ",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (selectedTimeText.isNotEmpty()) {
                            onEvent(CourseEvent.SetTime(selectedTimeText))
                            "$selectedTimeText  /  $selectedDateText"
                        } else {
                            ""
                        },
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                TypeDropdown(
                    courses = listOf("Flow Yoga", "Aerial Yoga", "Family Yoga"),
                    selectedDate = state.type,
                    onDateSelected = { onEvent(CourseEvent.SetType(it)) }
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
                    isError = state.capacity < 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = state.price.toString(),
                    onValueChange = {
                        onEvent(CourseEvent.SetPrice(if (it.isBlank()) 1 else it.toInt()))
                    },
                    placeholder = { Text(text = "Price") },
                    label = { Text(text = "Price") },
                    isError = state.price < 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = state.desc,
                    onValueChange = {
                        onEvent(CourseEvent.SetDesc(it))
                    },
                    placeholder = { Text(text = "Description") },
                    label = { Text(text = "Any description") }
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