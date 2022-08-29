package com.erapps.newws_compose.di

import com.erapps.newws.di.DefaultDispatcher
import com.erapps.newws.di.IoDispatcher
import com.erapps.newws_compose.api.services.NewsApiService
import com.erapps.newws_compose.data.source.top.ITopHeadLineDataSource
import com.erapps.newws_compose.data.source.top.ITopHeadLinesRepository
import com.erapps.newws_compose.data.source.top.TopHeadLinesRemoteDS
import com.erapps.newws_compose.data.source.top.TopHeadLinesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopHeadLinesModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteTopHeadLinesDataSource

    @Singleton
    @RemoteTopHeadLinesDataSource
    @Provides
    fun providesTopHeadLinesRemoteDS(
        newsApiService: NewsApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ITopHeadLineDataSource {
        return TopHeadLinesRemoteDS(newsApiService, ioDispatcher)
    }

    @Singleton
    @Provides
    fun providesTopHeadLinesRepository(
        @RemoteTopHeadLinesDataSource remoteTopHeadLinesDataSource: ITopHeadLineDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): ITopHeadLinesRepository {
        return TopHeadLinesRepository(
            remoteTopHeadLinesDataSource,
            defaultDispatcher
        )
    }
}