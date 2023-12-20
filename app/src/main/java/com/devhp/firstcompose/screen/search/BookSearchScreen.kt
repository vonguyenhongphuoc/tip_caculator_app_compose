package com.devhp.firstcompose.screen.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BookSearchScreen(navController: NavController = rememberNavController()) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.navigate(ReaderScreens.HomeScreen.name)
        }
    }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {

        }
    }
}