package com.devhp.firstcompose.screen.detail


import androidx.lifecycle.ViewModel
import com.devhp.firstcompose.data.Resource
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    suspend fun getBookInfo(bookID: String): Resource<Item> {
        return repository.getBookInfo(bookID)
    }

}