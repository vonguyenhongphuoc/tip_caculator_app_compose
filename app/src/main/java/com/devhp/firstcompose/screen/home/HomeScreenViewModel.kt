package com.devhp.firstcompose.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Book
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository) :
    ViewModel() {

    val data = MutableStateFlow<DataOrException<List<MBook>, Boolean, Exception>>(
        DataOrException(
            emptyList(), true, null
        )
    )

    init {
        Log.d("MyTag", "HomeScreenViewModel Init")
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            data.value.loading = false
            data.value = repository.getAllBooksFromDatabase()
            if (!data.value.data.isNullOrEmpty()) data.value.loading = false
            Log.d("MyTag", "getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MyTag", "HomeScreenViewModel onCleared")
    }
}