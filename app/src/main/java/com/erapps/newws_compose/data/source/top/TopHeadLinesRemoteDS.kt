package com.erapps.newws_compose.data.source.top

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.erapps.newws_compose.api.services.NewsApiService
import com.erapps.newws_compose.data.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface ITopHeadLineDataSource {

    suspend fun getTopNewByCategory(
        category: String
    ): Flow<PagingData<Article>>
}

class TopHeadLinesRemoteDS(
    private val newsApiService: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher
) : ITopHeadLineDataSource {

    override suspend fun getTopNewByCategory(
        category: String
    ): Flow<PagingData<Article>> {
        val pageSize = 30
        val maxSize = pageSize + (pageSize * 2)
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = pageSize,
                maxSize = maxSize
            ),
            pagingSourceFactory = {
                TopHeadLinesPagingSource(newsApiService, category = category)
            }
        ).flow.flowOn(ioDispatcher)
    }
}