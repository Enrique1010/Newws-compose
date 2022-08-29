package com.erapps.newws_compose.data.source.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.erapps.newws_compose.api.services.NewsApiService
import com.erapps.newws_compose.data.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface ISearchDataSource{
    fun getByUserQuery(q: String): Flow<PagingData<Article>>
}

class SearchDataSource(
    private val newsApiService: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher
): ISearchDataSource {
    override fun getByUserQuery(q: String): Flow<PagingData<Article>> {
        val pageSize = 30
        val maxSize = pageSize + (pageSize * 2)
        return Pager(
                PagingConfig(
                    pageSize = pageSize,
                    prefetchDistance = pageSize,
                    enablePlaceholders = true,
                    maxSize = maxSize
                )
            ) {
            SearchPagingSource(newsApiService = newsApiService, searchBy = q)
        }.flow.flowOn(ioDispatcher)
    }
}