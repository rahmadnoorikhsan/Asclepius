package com.rahmadev.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("image_uri")
    val imageUri: String,
    @ColumnInfo("label")
    val label: String,
    @ColumnInfo("score")
    val score: String,
)
