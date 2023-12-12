package com.devhp.firstcompose.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(message: String) {
    AlertDialog(
        title = { Text(text = "Error") },
        text = { Text(text = message) },
        onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ })
}