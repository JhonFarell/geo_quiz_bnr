package com.example.geoquiz.CheatsActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityCheatBinding

private lateinit var binding: ActivityCheatBinding


class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cheatsViewModel: CheatsViewModel by viewModels()

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.answerTextView.setText(cheatsViewModel.answerText)
        setResult(Activity.RESULT_OK, cheatsViewModel.setAnswerShownResult(cheatsViewModel.isCheater))

        binding.showAnswerButton.setOnClickListener{
           cheatsViewModel.setText()
            binding.answerTextView.setText(cheatsViewModel.answerText)
            setResult(Activity.RESULT_OK, cheatsViewModel.setAnswerShownResult(true))

        }

        cheatsViewModel.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}