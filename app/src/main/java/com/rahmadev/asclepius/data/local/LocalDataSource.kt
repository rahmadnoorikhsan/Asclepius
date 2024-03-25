package com.rahmadev.asclepius.data.local

import com.rahmadev.asclepius.data.local.entity.ResultEntity
import com.rahmadev.asclepius.data.local.room.ResultDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val resultDao: ResultDao,
) {

    suspend fun insertResult(resultEntity: ResultEntity) = resultDao.insertResult(resultEntity)

    fun getAllResults() = resultDao.getAllResults()
}