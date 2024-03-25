package com.rahmadev.asclepius.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahmadev.asclepius.data.local.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultEntity: ResultEntity)

    @Query("SELECT * FROM resultentity ORDER BY id DESC")
    fun getAllResults(): Flow<List<ResultEntity>>
}