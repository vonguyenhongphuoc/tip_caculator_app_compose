package com.devhp.firstcompose.screen.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Weather

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {
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
        Text(text = "Main Screen ${weatherData.data!!.city!!.country}")
    }
}
