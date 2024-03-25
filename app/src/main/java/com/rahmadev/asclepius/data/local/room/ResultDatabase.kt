package com.rahmadev.asclepius.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahmadev.asclepius.data.local.entity.ResultEntity

@Database(entities = [ResultEntity::class], version = 1)
abstract class ResultDatabase: RoomDatabase() {

    abstract fun resultDao(): ResultDao
}