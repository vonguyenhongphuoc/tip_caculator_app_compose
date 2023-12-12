package com.devhp.firstcompose.network

import com.devhp.firstcompose.model.WeatherObject
import com.devhp.firstcompose.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid:String = Constants.API_KEY
    ): WeatherObject
}