package com.erapps.newws_compose.api.services

import com.erapps.newws.data.models.ErrorModel
import com.erapps.newws.data.models.GetAllModel
import com.erapps.newws_compose.api.NetworkResponse
import com.erapps.newws_compose.data.models.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun getAllNews(
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int?,
        @Query("q") searchBy: String?
    ): NetworkResponse<GetAllModel<Article>, ErrorModel>

    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("category") category: String?,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): NetworkResponse<GetAllModel<Article>, ErrorModel>
}