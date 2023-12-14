package com.devhp.firstcompose.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Weather
import com.devhp.firstcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    val data: MutableState<DataOrException<Weather, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, null)
    )

//    init {
//          loadData()
//    }

    private fun loadData() {
        viewModelScope.launch {
            coroutineScope {
                data.value.loading = true
                withContext(Dispatchers.IO){
                    data.value = repository.getWeather("Seattle")
                }
                if(data.value.data != null) data.value.loading = false
            }
        }

    }

    suspend fun getWeatherData(city: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)
    }

}