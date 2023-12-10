package com.devhp.firstcompose.network

import com.devhp.firstcompose.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestion() : Question
}