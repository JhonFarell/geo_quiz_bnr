package com.example.geoquiz.CheatsActivity

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.geoquiz.R

const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"



class CheatsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    var answerIsTrue
    get() = savedStateHandle.get(EXTRA_ANSWER_IS_TRUE) ?: false
    set(value) = savedStateHandle.set(EXTRA_ANSWER_IS_TRUE, value)

    var answerText = R.string.default_text

    var isCheater
    get() = savedStateHandle.get(EXTRA_ANSWER_SHOWN) ?: false
    set(value) = savedStateHandle.set(EXTRA_ANSWER_SHOWN, value)


    fun setAnswerShownResult (isAnswerShown: Boolean): Intent {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        isCheater = true
        return data
    }

    fun setText() {
        answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
    }
}