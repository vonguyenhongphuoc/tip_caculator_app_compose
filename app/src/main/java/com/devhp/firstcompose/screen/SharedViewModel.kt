package com.devhp.firstcompose.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.Resource
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {
    private val _bookInfo = MutableStateFlow<Resource<Item>>(Resource.Loading(data = null))
    val bookInfo: StateFlow<Resource<Item>> = _bookInfo

    init {
        Log.d("MyTag", "SharedViewModel Init")
    }

    suspend fun getBookInfo(bookID: String) {
        Log.d("MyTag", "BookID: $bookID")
        viewModelScope.launch(Dispatchers.IO) {
            _bookInfo.value = bookRepository.getBookInfo(bookID)
            Log.d("getBookInfo", "${bookInfo.value.data}")
        }
    }
}