package com.devhp.firstcompose.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    RegisterScreen,
    HomeScreen,
    BookSearchScreen,
    BookDetailScreen,
    BookUpdateScreen,
    StatScreen;

    companion object {
        fun fromRoute(route: String): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            RegisterScreen.name -> RegisterScreen
            HomeScreen.name -> HomeScreen
            BookSearchScreen.name -> BookSearchScreen
            BookDetailScreen.name -> BookDetailScreen
            BookUpdateScreen.name -> BookUpdateScreen
            StatScreen.name -> StatScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is note recognized")
        }
    }
}