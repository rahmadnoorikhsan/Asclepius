package com.rahmadev.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rahmadev.asclepius.domain.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val resultRepository: ResultRepository
) : ViewModel() {

    fun getAllResults() = resultRepository.getAllResults().asLiveData()
}