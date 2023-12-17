package com.devhp.firstcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devhp.firstcompose.model.Favorite
import com.devhp.firstcompose.model.UnitWeather


@Database(entities = [Favorite::class, UnitWeather::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}