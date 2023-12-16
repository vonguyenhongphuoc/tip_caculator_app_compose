package com.devhp.firstcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devhp.firstcompose.model.Favorite
import com.devhp.firstcompose.model.UnitWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM fav_tbl WHERE city =:city")
    suspend fun getFavByID(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // Unit table
    @Query("SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<UnitWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: UnitWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: UnitWeather)

    @Query("DELETE FROM settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: UnitWeather)
}