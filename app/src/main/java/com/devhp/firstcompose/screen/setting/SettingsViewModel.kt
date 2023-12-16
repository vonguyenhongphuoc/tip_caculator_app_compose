package com.devhp.firstcompose.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.model.UnitWeather

import com.devhp.firstcompose.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository) :
    ViewModel() {
    private val _unitWeatherList = MutableStateFlow<List<UnitWeather>>(emptyList())
    val unitList = _unitWeatherList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listOfUnits ->
                if (listOfUnits.isEmpty()) {
                    Log.d("MyTag", ":Empty List")
                } else {
                    _unitWeatherList.value = listOfUnits
                }
            }
        }
    }

    fun insertUnit(unitWeather: UnitWeather) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUnit(unitWeather)
    }
    fun updateUnit(unit: UnitWeather) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUnit(unit)
    }
    fun deleteUnit(unit: UnitWeather) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteUnit(unit)
    }

    fun deleteAllUnits() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllUnits()
    }
}