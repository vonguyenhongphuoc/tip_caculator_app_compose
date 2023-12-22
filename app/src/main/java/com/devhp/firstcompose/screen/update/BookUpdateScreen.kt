package com.devhp.firstcompose.screen.update

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.devhp.firstcompose.component.ReaderAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookUpdateScreen(navController: NavController,bookID: String) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Update Book",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        )
    }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {

        }
    }

}