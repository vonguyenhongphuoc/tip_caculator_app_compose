package com.devhp.firstcompose.screen.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.City
import com.devhp.firstcompose.model.Weather
import com.devhp.firstcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
 suspend fun getWeatherData(city: String) : DataOrException<Weather, Boolean, Exception>{
     return repository.getWeather(cityQuery = city)
 }

}