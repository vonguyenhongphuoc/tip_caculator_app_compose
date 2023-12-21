package com.devhp.firstcompose.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(navController: NavController, bookID: String) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Books Details",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                showProfile = false
            ) {
                navController.navigate(ReaderScreens.BookSearchScreen.name)
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Book Description: $bookID")
            }

        }
    }
}