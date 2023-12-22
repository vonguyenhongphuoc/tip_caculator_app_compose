package com.devhp.firstcompose.screen.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.data.Resource
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {
    var list: List<Item> by mutableStateOf(listOf())
    var isLoading = MutableStateFlow(true)


    init {
        Log.d("MyTag", "BookSearchViewModel Init")
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            if (query.isBlank()) return@launch
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty())  isLoading.value = false
                    }

                    is Resource.Error -> {
                       isLoading.value = false
                        Log.d(
                            "searchBooks",
                            "searchBooks: Failed getting Books: ${response.message}"
                        )
                    }

                    else -> {
                        isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                Log.d("searchBooks exception:", e.message.toString())
            }

        }
    }

}


//var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> = mutableStateOf(
//    DataOrException(null, true, Exception(""))
//)
//
//init {
//    searchBooks("android")
//}
//
//fun searchBooks(query: String) {
//    viewModelScope.launch(Dispatchers.IO) {
//        if (query.isBlank()) {
//            return@launch
//        }
//        withContext(Dispatchers.Main) {
//            listOfBooks.value.loading = true
//        }
//        listOfBooks.value = repository.getBooks(query)
//        Log.d("MyTag", "SearchBooks: ${listOfBooks.value.data.toString()}")
//        if (listOfBooks.value.data.toString().isNotEmpty()) withContext(Dispatchers.Main) {
//            listOfBooks.value.loading = false
//        }
//    }
//}