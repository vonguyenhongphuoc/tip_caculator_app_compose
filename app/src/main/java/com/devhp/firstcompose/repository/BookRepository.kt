package com.devhp.firstcompose.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.data.Resource
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {
    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = "Loading...")
            val itemList = api.getAllBooks(searchQuery).items
            if (!itemList.isNullOrEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }

    suspend fun getBookInfo(bookID: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookID)
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}


//class BookRepository @Inject constructor(private val api: BooksApi) {
//    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
//    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
//
//
//    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
//        try {
//            dataOrException.loading = true
//            dataOrException.data = api.getAllBooks(searchQuery).items
//        } catch (e: Exception) {
//            dataOrException.e = e
//        }
//
//        return dataOrException
//    }
//
//    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
//        try {
//            bookInfoDataOrException.loading = true
//            bookInfoDataOrException.data = api.getBookInfo(bookId)
//        } catch (e: Exception) {
//            bookInfoDataOrException.e = e
//        }
//
//        return bookInfoDataOrException
//    }
//}