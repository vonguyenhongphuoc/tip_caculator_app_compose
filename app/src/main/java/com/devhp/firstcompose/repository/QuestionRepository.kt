package com.devhp.firstcompose.repository

import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.QuestionItem
import com.devhp.firstcompose.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean,Exception>()
}