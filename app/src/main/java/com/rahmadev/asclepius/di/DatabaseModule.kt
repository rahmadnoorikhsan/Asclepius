package com.rahmadev.asclepius.di

import android.content.Context
import androidx.room.Room
import com.rahmadev.asclepius.data.local.room.ResultDao
import com.rahmadev.asclepius.data.local.room.ResultDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideResultDatabase(@ApplicationContext context: Context): ResultDatabase =
        Room.databaseBuilder(context, ResultDatabase::class.java, "result_db").fallbackToDestructiveMigration().build()

    @Provides
    fun provideResultDao(resultDatabase: ResultDatabase): ResultDao = resultDatabase.resultDao()
}