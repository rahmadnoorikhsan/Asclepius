package com.rahmadev.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rahmadev.asclepius.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun getArticles() = articleRepository.getArticles().asLiveData()
}