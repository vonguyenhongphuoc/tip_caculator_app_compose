package com.devhp.firstcompose.repository

import android.util.Log
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.MBook
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(private val queryBook: CollectionReference) {


    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException
    }

    suspend fun getBooksByDocumentID(documentID: String) : DataOrException<MBook, Boolean, Exception>{
        val dataOrException = DataOrException<MBook, Boolean, Exception>()
        try {
            dataOrException.loading = true
           val documentSnapshot =  queryBook.document(documentID).get().await()
            dataOrException.data = documentSnapshot.toObject(MBook::class.java)
            if (dataOrException.data != null) dataOrException.loading = false
        }catch (exception:Exception){
            dataOrException.e = exception
        }
        return dataOrException
    }

    suspend fun listenToBooksChangeFromServer(): Flow<DataOrException<List<MBook>, Boolean, Exception>> =
        callbackFlow {
            var count = 0
            val listenerChangeBooks = queryBook.addSnapshotListener { value, error ->
                val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()
                count++
                Log.d("MyTag", "Running...$count")
                if(count >= 3){
                    Log.d("MyTag", "Run into...$count")
                    if (error != null) {
                        dataOrException.e = error
                        dataOrException.loading = false
                        trySend(dataOrException)
                    } else if (value != null) {
                        dataOrException.data =
                            value.documents.mapNotNull { it.toObject(MBook::class.java) }
                        dataOrException.loading = false
                        trySend(dataOrException)
                    }
                    count = 1
                }

            }
            awaitClose { listenerChangeBooks.remove() }

        }
}