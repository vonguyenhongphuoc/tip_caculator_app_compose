package com.devhp.firstcompose.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
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
import com.devhp.firstcompose.screen.update.UpdateViewModel

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val homeViewModel = hiltViewModel<HomeScreenViewModel>()

    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        Log.d("MyTag", "NavHost Init")
        composable(ReaderScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
//            entry.savedStateHandle.get<Boolean>("refresh_data").let {
//                if (it == true){
//                    homeViewModel.getAllBooksFromDatabase()
//                }
//                entry.savedStateHandle.remove<String>("refresh_data")
//            }

            HomeScreen(
                navController = navController,
                homeViewModel
            )
        }
        composable(ReaderScreens.StatScreen.name) {

            StatScreen(navController = navController, homeViewModel = homeViewModel)
        }
        composable(ReaderScreens.BookSearchScreen.name) {
            val bookSearchViewModel = hiltViewModel<BookSearchViewModel>()
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

        val updateScreenName = ReaderScreens.BookUpdateScreen.name
        composable("$updateScreenName/{documentID}", arguments = listOf(navArgument("documentID") {
            type = NavType.StringType
        })) { backStackEntry ->
            val updateViewModel = hiltViewModel<UpdateViewModel>()
            backStackEntry.arguments?.getString("documentID").let {
                BookUpdateScreen(
                    navController = navController,
                    updateViewModel = updateViewModel,
                    documentID = it.toString()
                )
            }
        }


    }
}