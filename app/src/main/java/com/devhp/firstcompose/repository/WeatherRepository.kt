package com.devhp.firstcompose.repository

import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.WeatherObject
import com.devhp.firstcompose.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<WeatherObject, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}