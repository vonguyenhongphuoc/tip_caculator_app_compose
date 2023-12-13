package com.devhp.firstcompose.screen.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.WeatherObject
import com.devhp.firstcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    val data: MutableState<DataOrException<WeatherObject, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeather("Seattle")
    }

    private fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
            Log.d("MyTag", "getWeather: ${data.value.data.toString()}")
        }
    }

}