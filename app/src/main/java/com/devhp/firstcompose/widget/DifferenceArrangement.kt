package com.devhp.firstcompose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun ArrangementExample() {
    // Đặt ngôn ngữ thành Ả Rập
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("A", modifier = Modifier.background(Color.Red), color = Color.White)
                Text("B", modifier = Modifier.background(Color.Green), color = Color.White)
                Text("C", modifier = Modifier.background(Color.Blue), color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Text("A", modifier = Modifier.background(Color.Red), color = Color.White)
                Text("B", modifier = Modifier.background(Color.Green), color = Color.White)
                Text("C", modifier = Modifier.background(Color.Blue), color = Color.White)
            }
        }
    }
}