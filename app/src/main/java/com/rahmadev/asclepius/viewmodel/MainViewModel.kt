package com.rahmadev.asclepius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahmadev.asclepius.domain.model.Result
import com.rahmadev.asclepius.domain.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resultRepository: ResultRepository
) : ViewModel() {

    fun insertResult(result: Result) = viewModelScope.launch {
        resultRepository.insertResult(result)
    }
}