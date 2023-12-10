package com.devhp.firstcompose.repository

import android.util.Log
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.QuestionItem
import com.devhp.firstcompose.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestion()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.e = e
            Log.d("Exc", "getAllQuestions: ${dataOrException.e?.localizedMessage}")
        }
        return dataOrException
    }
}