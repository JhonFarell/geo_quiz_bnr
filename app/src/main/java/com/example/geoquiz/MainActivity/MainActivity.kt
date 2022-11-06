package com.example.geoquiz.MainActivity

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.geoquiz.*
import com.example.geoquiz.CheatsActivity.CheatActivity
import com.example.geoquiz.CheatsActivity.EXTRA_ANSWER_SHOWN
import com.example.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            mainViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.fillListWithNulls()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.version.text = "API level: ${mainViewModel.checkOsVersion()}"
        binding.cheatCounter.text = "Cheats left: ${mainViewModel.numberOfCheatsLeft}"


        binding.trueButton.setOnClickListener {
            if (mainViewModel.currentUserPosition==null){
                checkAnswer(true)
            } else {
                Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT).show()

            }
        }

        binding.falseButton.setOnClickListener {
            if (mainViewModel.currentUserPosition==null) {
                checkAnswer(false)
            } else
            Toast.makeText(this, R.string.already_answered, Toast.LENGTH_SHORT).show()

        }

        binding.nextButton.setOnClickListener {
            if (mainViewModel.currentIndex<(mainViewModel.questionBank.size-1)) {
                mainViewModel.moveTo(true)
                updateQuestion()
            } else {
                    Toast.makeText(this, "${mainViewModel.results(mainViewModel.answers)}% of correct answers!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.previousButton.setOnClickListener {
            if (mainViewModel.currentIndex == 0) {
                Toast.makeText(this, R.string.nope, Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.moveTo(false)
                updateQuestion()
            }
        }

        binding.questionTextView.setOnClickListener {
            Toast.makeText(this, mainViewModel.nextQuestion, Toast.LENGTH_SHORT).show()
        }

        binding.cheatButton.setOnClickListener {

            if (mainViewModel.numberOfCheatsLeft==0) {
                Toast.makeText(this, R.string.no_cheats_left, Toast.LENGTH_SHORT).show()
            } else {
            val answerIsTrue = mainViewModel.correctQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
            }
        }

        updateQuestion()
        mainViewModel.setCheatMark()

    }

    private fun updateQuestion() {
        val questionResId = mainViewModel.currentQuestionText
        binding.questionTextView.setText(questionResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        when {
            mainViewModel.cheatedAQuestions[mainViewModel.currentIndex] -> {
                Utils().makeToast(this, R.string.judgment_toast)
                mainViewModel.currentUserPosition = 1
            }
            userAnswer == mainViewModel.correctQuestionAnswer -> {
                Utils().makeToast(this, R.string.correct_toast)
                mainViewModel.currentUserPosition = 1
            }
            else -> {
            Utils().makeToast(this, R.string.incorrect_toast)
            mainViewModel.currentUserPosition = 0
            }
        }
}

    override fun onResume() {
        super.onResume()
        mainViewModel.setCheatMark()
        binding.cheatCounter.text = "Cheats left: ${mainViewModel.numberOfCheatsLeft}"
    }


}