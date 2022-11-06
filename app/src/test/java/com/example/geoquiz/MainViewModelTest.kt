package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class MainViewModelTest {
    @Test
    fun providesExpectedMainActivityVM() {
        val savedStateHandle = SavedStateHandle()
        val mainViewModel = MainViewModel(savedStateHandle)
        assert(R.string.question_australia==mainViewModel.currentQuestionText)
    }

    @Test
    fun checkMoveToFunctionMainActivityVM() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val mainViewModel = MainViewModel(savedStateHandle)
        assert(R.string.question_asia==mainViewModel.currentQuestionText)
        mainViewModel.moveTo(false)
        assert(R.string.question_americas==mainViewModel.currentQuestionText)
    }

    @Test
    fun checkCorrectAnswer(){
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 3))
        val mainViewModel = MainViewModel(savedStateHandle)
        assert(!mainViewModel.correctQuestionAnswer)
    }
}