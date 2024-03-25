package com.rahmadev.asclepius.data.remote.retrofit

import com.rahmadev.asclepius.data.remote.response.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String = "cancer",
        @Query("category") category: String = "health",
        @Query("language") language: String = "en",
    ): Response<ArticleResponse>
}