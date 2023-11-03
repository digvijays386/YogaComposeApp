package com.digvijay.yogaadminapp.ui.screen

import android.provider.SyncStateContract.Columns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCourseDialog(
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ },
        title = { "Add Yoga Course" },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                TextField(value = , onValueChange = )
            }
        })
    
}