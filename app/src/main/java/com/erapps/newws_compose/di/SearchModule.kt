package com.erapps.newws_compose.di

import com.erapps.newws.di.DefaultDispatcher
import com.erapps.newws.di.IoDispatcher
import com.erapps.newws_compose.api.services.NewsApiService
import com.erapps.newws_compose.data.source.search.ISearchDataSource
import com.erapps.newws_compose.data.source.search.ISearchRepository
import com.erapps.newws_compose.data.source.search.SearchDataSource
import com.erapps.newws_compose.data.source.search.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteNewsDataSource

    @Singleton
    @RemoteNewsDataSource
    @Provides
    fun provideNewsDataSource(
        newsApiService: NewsApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ISearchDataSource {
        return SearchDataSource(newsApiService, ioDispatcher)
    }

    @Singleton
    @Provides
    fun providesNewsRepository(
        @RemoteNewsDataSource remoteNewsDataSource: ISearchDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): ISearchRepository {
        return SearchRepository(
            remoteNewsDataSource,
            defaultDispatcher
        )
    }
}