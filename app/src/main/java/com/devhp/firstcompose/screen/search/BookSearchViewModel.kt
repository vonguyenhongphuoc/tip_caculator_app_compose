package com.devhp.firstcompose.screen.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {
    var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isBlank()) {
                return@launch
            }
            withContext(Dispatchers.Main) {
                listOfBooks.value.loading = true
            }
            listOfBooks.value = repository.getBooks(query)
            Log.d("MyTag", "SearchBooks: ${listOfBooks.value.data.toString()}")
            if (listOfBooks.value.data.toString().isNotEmpty()) withContext(Dispatchers.Main) {
                listOfBooks.value.loading = false
            }
        }
    }
}