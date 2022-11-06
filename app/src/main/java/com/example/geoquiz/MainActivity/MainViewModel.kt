package com.example.geoquiz.MainActivity

import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import kotlin.math.roundToInt

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val CHEAT_COUNTER = "CHEAT_COUNTER"

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val questionBank = listOf(
        QuestionsModel(R.string.question_australia, true),
        QuestionsModel(R.string.question_oceans, true),
        QuestionsModel(R.string.question_mideast, false),
        QuestionsModel(R.string.question_africa, false),
        QuestionsModel(R.string.question_americas, true),
        QuestionsModel(R.string.question_asia, true)
    )

    var isCheater
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    fun setCheatMark() {
        if (isCheater) {
            cheatedAQuestions[currentIndex] = true
            isCheater = false
            var reduceCheatCounter = numberOfCheatsLeft-1
            numberOfCheatsLeft = reduceCheatCounter
        }
    }

    var numberOfCheatsLeft
    get() = savedStateHandle.get(CHEAT_COUNTER) ?: 3
    set(value) = savedStateHandle.set(CHEAT_COUNTER,value)


    val currentQuestionText: Int
        get() = questionBank[currentIndex].stringResId

    val correctQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val nextQuestion: Int
        get() = questionBank[currentIndex + 1].stringResId

    val answers = mutableListOf<Int?>(null)
    val cheatedAQuestions = mutableListOf<Boolean> (false)

    var currentUserPosition: Int?
    get() = answers[currentIndex]
    set(value) {
            answers[currentIndex]=value
    }


    fun moveTo (direction: Boolean) {
        if (direction) {
            currentIndex += 1
        } else {currentIndex -= 1}
    }

    fun fillListWithNulls () {
    while (answers.size!= questionBank.size) {
        answers.add(null)
        cheatedAQuestions.add(false)
    }
    }

    fun results(answers: List<Int?>): Int {
        val normalizedAnswers = answers.filterNotNull()
        val score = normalizedAnswers.sum()
        val score2: Float = score / questionBank.size.toFloat()
        return (score2 * 100).roundToInt()
    }

    fun checkOsVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }
}