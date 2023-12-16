package com.devhp.firstcompose.repository

import com.devhp.firstcompose.data.WeatherDao
import com.devhp.firstcompose.model.Favorite
import com.devhp.firstcompose.model.UnitWeather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    // Favorite Table
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun getFavByID(city: String): Favorite = weatherDao.getFavByID(city)


    // Unit Table

    fun getUnits(): Flow<List<UnitWeather>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: UnitWeather) = weatherDao.insertUnit(unit)

    suspend fun updateUnit(unit: UnitWeather) = weatherDao.updateUnit(unit)

    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

    suspend fun deleteUnit(unit: UnitWeather) = weatherDao.deleteUnit(unit)
}