package com.dicoding.asclepius.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val imageUri: Uri? = null,
    val label: String,
    val score: String,
): Parcelable
