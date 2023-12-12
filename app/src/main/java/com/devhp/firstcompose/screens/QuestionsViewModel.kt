package com.devhp.firstcompose.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.QuestionItem
import com.devhp.firstcompose.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            try {
                val deferredData =
                    async(Dispatchers.IO) {
                        repository.getAllQuestions()
                    }
                data.value = deferredData.await()
            } finally {
                data.value.loading = false
            }
        }
    }

    fun getTotalQuestionCount(): Int {
        return data.value.data?.size ?: 0
    }
}