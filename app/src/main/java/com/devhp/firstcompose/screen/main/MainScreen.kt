package com.devhp.firstcompose.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Weather
import com.devhp.firstcompose.widget.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    /* Solution 1*/
//    val weatherData = mainViewModel.data.value
    /* Solution 2*/
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(
            null,
            true,
            null
        ), producer = {
            value = mainViewModel.getWeatherData("Seattle")
        }).value
    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city?.name + ",${weather.city?.country}",
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            MainContent(weather)
        }
    }
}

@Composable
fun MainContent(weather: Weather) {

}
