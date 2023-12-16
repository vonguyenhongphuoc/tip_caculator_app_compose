package com.devhp.firstcompose.screen.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.model.Favorite
import com.devhp.firstcompose.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDBRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    Log.d("MyTag", ": Empty favs")
                } else {
                    _favList.value = listOfFavs
                    Log.d("MyTag", "${favList.value}")
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavorite(favorite)
    }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateFavorite(favorite)
    }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavorite(favorite)
    }
    
}