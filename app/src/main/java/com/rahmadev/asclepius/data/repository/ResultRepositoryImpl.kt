package com.rahmadev.asclepius.data.repository

import com.rahmadev.asclepius.data.local.LocalDataSource
import com.rahmadev.asclepius.domain.model.Result
import com.rahmadev.asclepius.domain.repository.ResultRepository
import com.rahmadev.asclepius.helper.DataMapper
import com.rahmadev.asclepius.helper.DataMapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : ResultRepository {
    override suspend fun insertResult(result: Result) =
        localDataSource.insertResult(result.toEntity())

    override fun getAllResults(): Flow<List<Result>> = localDataSource.getAllResults().map { results ->
        results.map {
            DataMapper.mapResultEntityToResult(it)
        }
    }.flowOn(Dispatchers.IO)
}