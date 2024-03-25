package com.rahmadev.asclepius.data.repository

import com.rahmadev.asclepius.BuildConfig
import com.rahmadev.asclepius.data.remote.RemoteDataSource
import com.rahmadev.asclepius.domain.model.Article
import com.rahmadev.asclepius.domain.repository.ArticleRepository
import com.rahmadev.asclepius.helper.DataMapper
import com.rahmadev.asclepius.helper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : ArticleRepository {
    override fun getArticles(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        try {
            val apiKey = BuildConfig.API_KEY
            val response = remoteDataSource.getArticles(apiKey)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    emit(Resource.Success(result.articles.map {
                        DataMapper.mapArticleResponseItemToArticle(
                            it
                        )
                    }))
                }
            } else {
                val responseCode = response.code()
                emit(Resource.Error(response.message(), responseCode))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.message, errorCode = 404))
        } catch (e: HttpException) {
            emit(Resource.Error("Server bermasalah", e.code()))
        }
    }.flowOn(Dispatchers.IO)
}