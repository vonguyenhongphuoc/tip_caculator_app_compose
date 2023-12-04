package com.devhp.firstcompose.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devhp.firstcompose.model.Movie
import com.devhp.firstcompose.model.getMovies
import com.devhp.firstcompose.navigation.MovieScreens
import com.devhp.firstcompose.util.MyToast
import com.devhp.firstcompose.widgets.MovieRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController ){
    Scaffold(topBar = {
        Surface(shadowElevation = 5.dp) {
            TopAppBar(
                title = { Text("Movies") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
            )
        }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            MainContent(navController)
        }
    }
}


@Composable
fun MainContent(
    navController: NavController,
    movieList: List<Movie> = getMovies()
) {
    val context = LocalContext.current
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movieList) {
                MovieRow(movie = it) { movie ->
                    MyToast.showToast(context, movie)
                    navController.navigate(MovieScreens.DetailScreen.name+"/$movie")
                }
            }
        }
    }
}

