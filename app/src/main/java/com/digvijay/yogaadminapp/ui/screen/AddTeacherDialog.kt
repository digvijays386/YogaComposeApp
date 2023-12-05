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
import com.digvijay.yogaadminapp.utills.TeacherEvent
import com.digvijay.yogaadminapp.utills.TeacherState

@Composable
fun AddTeacherDialog(
    state: TeacherState,
    onEvent: (TeacherEvent) -> Unit,
) {
    AlertDialog(onDismissRequest = { onEvent(TeacherEvent.HideDialog) },
        title = { Text(text = "Teacher Detail") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(TeacherEvent.SetName(it))
                    },
                    placeholder = { Text(text = "Name") },
                    label = { Text(text = "Name") },
                    isError = state.name.isBlank(),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                )
            }
        },
        confirmButton = {
            Button(onClick = { onEvent(TeacherEvent.Save) }) {
                Text(text = "Save Teacher")
            }
        }
    )
}