package com.rahmadev.asclepius.helper

import android.net.Uri
import com.rahmadev.asclepius.data.local.entity.ResultEntity
import com.rahmadev.asclepius.data.remote.response.ArticlesItem
import com.rahmadev.asclepius.domain.model.Article
import com.rahmadev.asclepius.domain.model.Result

object DataMapper {
    fun mapResultEntityToResult(data: ResultEntity) =
        Result(
            data.id,
            Uri.parse(data.imageUri),
            data.label,
            data.score
        )

    fun Result.toEntity() =
        ResultEntity(
            id,
            imageUri.toString(),
            label,
            score
        )

    fun mapArticleResponseItemToArticle(data: ArticlesItem?) =
        Article(
            data?.title ?: "",
            data?.urlToImage ?: "",
            data?.url ?: "",
            data?.author ?: ""
        )
}