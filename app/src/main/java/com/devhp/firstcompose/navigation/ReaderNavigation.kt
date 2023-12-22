package com.devhp.firstcompose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devhp.firstcompose.screen.SharedViewModel
import com.devhp.firstcompose.screen.detail.BookDetailScreen
import com.devhp.firstcompose.screen.detail.DetailsViewModel
import com.devhp.firstcompose.screen.home.HomeScreen
import com.devhp.firstcompose.screen.home.HomeScreenViewModel
import com.devhp.firstcompose.screen.login.LoginScreen
import com.devhp.firstcompose.screen.search.BookSearchScreen
import com.devhp.firstcompose.screen.search.BookSearchViewModel
import com.devhp.firstcompose.screen.slash.SplashScreen
import com.devhp.firstcompose.screen.stat.StatScreen
import com.devhp.firstcompose.screen.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val bookSearchViewModel = hiltViewModel<BookSearchViewModel>()

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        Log.d("MyTag", "NavHost Init")
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController)
        }
        composable(ReaderScreens.StatScreen.name) {
            StatScreen(navController = navController)
        }
        composable(ReaderScreens.BookSearchScreen.name) {
            BookSearchScreen(navController = navController, viewModel = bookSearchViewModel)
        }
        val detailScreenName = ReaderScreens.BookDetailScreen.name
        composable("$detailScreenName/{bookID}", arguments = listOf(navArgument("bookID") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookID").let {
                val viewModel = hiltViewModel<DetailsViewModel>()
                BookDetailScreen(
                    navController = navController,
                    bookID = it.toString(),
                    viewModel = viewModel,
                    sharedViewModel = sharedViewModel
                )
            }

        }

        val updateScreenName= ReaderScreens.BookUpdateScreen.name
        composable("$updateScreenName/{bookID}", arguments = listOf(navArgument("bookID"){
            type = NavType.StringType
        })){
            backStackEntry ->
            backStackEntry.arguments?.getString("bookID").let {
                BookUpdateScreen(navController = navController, bookID = it.toString())
            }
        }


    }
}