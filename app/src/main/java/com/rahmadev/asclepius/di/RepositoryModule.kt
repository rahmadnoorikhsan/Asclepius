package com.rahmadev.asclepius.di

import com.rahmadev.asclepius.data.repository.ArticleRepositoryImpl
import com.rahmadev.asclepius.data.repository.ResultRepositoryImpl
import com.rahmadev.asclepius.domain.repository.ArticleRepository
import com.rahmadev.asclepius.domain.repository.ResultRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideResultRepository(resultRepositoryImpl: ResultRepositoryImpl): ResultRepository

    @Binds
    abstract fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}