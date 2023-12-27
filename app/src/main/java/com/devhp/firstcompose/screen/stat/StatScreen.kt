package com.devhp.firstcompose.screen.stat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.model.Book
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.screen.home.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatScreen(navController: NavHostController, homeViewModel: HomeScreenViewModel) {

    val booksData = homeViewModel.data.collectAsState().value.data
    var books : List<MBook> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Stats",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                showProfile = false
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            if(!booksData.isNullOrEmpty()){
               books = booksData.filter {
                    mBook -> mBook.userId == currentUser?.uid
                }
            }

            Row {

            }
        }
    }
}