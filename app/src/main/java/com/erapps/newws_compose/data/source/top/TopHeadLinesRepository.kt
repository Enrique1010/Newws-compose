package com.erapps.newws_compose.data.source.top

import androidx.paging.PagingData
import com.erapps.newws_compose.data.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface ITopHeadLinesRepository {

    suspend fun getTopNewsByCategory(
        category: String
    ): Flow<PagingData<Article>>
}

class TopHeadLinesRepository(
    private val remoteDS: ITopHeadLineDataSource,
    private val defaultDispatcher: CoroutineDispatcher
): ITopHeadLinesRepository {

    override suspend fun getTopNewsByCategory(
        category: String
    ): Flow<PagingData<Article>> {
        return remoteDS.getTopNewByCategory(category).flowOn(defaultDispatcher)
    }
}