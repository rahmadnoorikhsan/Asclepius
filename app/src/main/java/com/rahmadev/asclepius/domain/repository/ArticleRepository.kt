package com.rahmadev.asclepius.domain.repository

import com.rahmadev.asclepius.domain.model.Article
import com.rahmadev.asclepius.helper.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(): Flow<Resource<List<Article>>>
}