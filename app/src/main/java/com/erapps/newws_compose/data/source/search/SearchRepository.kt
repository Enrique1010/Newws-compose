package com.erapps.newws_compose.data.source.search

import androidx.paging.PagingData
import com.erapps.newws_compose.data.models.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface ISearchRepository{
    fun getUserByQuery(q: String): Flow<PagingData<Article>>
}

class SearchRepository(
    private val newsDs: ISearchDataSource,
    private val defaultDispatcher: CoroutineDispatcher
): ISearchRepository {
    override fun getUserByQuery(q: String): Flow<PagingData<Article>> {
        return newsDs.getByUserQuery(q).flowOn(defaultDispatcher)
    }
}