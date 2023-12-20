package com.devhp.firstcompose.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()


    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
        } catch (e: Exception) {
            dataOrException.e = e
        }

        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookId)
        } catch (e: Exception) {
            bookInfoDataOrException.e = e
        }

        return bookInfoDataOrException
    }
}