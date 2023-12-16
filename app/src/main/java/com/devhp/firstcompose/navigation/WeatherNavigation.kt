package com.devhp.firstcompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devhp.firstcompose.screen.main.MainScreen
import com.devhp.firstcompose.screen.main.MainViewModel
import com.devhp.firstcompose.screen.search.SearchScreen
import com.devhp.firstcompose.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }
        val mainRoute = WeatherScreens.MainScreen.name
        composable("$mainRoute/{city}?", arguments = listOf(navArgument(name = "city"){
            type = NavType.StringType
            defaultValue = null
            nullable = true
        })) {
            navBack ->
            val city = navBack.arguments?.getString("city")
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel, city)
        }
        composable(mainRoute) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel, null)
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}