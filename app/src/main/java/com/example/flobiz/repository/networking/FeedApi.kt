package com.example.flobiz.repository.networking

import com.example.flobiz.models.QuestionFeed
import retrofit2.Call
import retrofit2.http.GET

interface FeedApi {
    @GET("questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow")
    fun getQuestionFeed(): Call<QuestionFeed>
}