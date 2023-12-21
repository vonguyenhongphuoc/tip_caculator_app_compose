package com.devhp.firstcompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devhp.firstcompose.screen.home.HomeScreen
import com.devhp.firstcompose.screen.login.LoginScreen
import com.devhp.firstcompose.screen.search.BookSearchScreen
import com.devhp.firstcompose.screen.search.BookSearchViewModel
import com.devhp.firstcompose.screen.slash.SplashScreen
import com.devhp.firstcompose.screen.stat.StatScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(ReaderScreens.StatScreen.name) {
            StatScreen(navController = navController)
        }
        composable(ReaderScreens.BookSearchScreen.name) {
            val viewModel = hiltViewModel<BookSearchViewModel>()
            BookSearchScreen(navController = navController, viewModel = viewModel)
        }

    }
}