package com.rahmadev.asclepius.domain.repository

import com.rahmadev.asclepius.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface ResultRepository {

    suspend fun insertResult(result: Result)

    fun getAllResults(): Flow<List<Result>>
}