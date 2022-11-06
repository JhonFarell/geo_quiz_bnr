package com.example.geoquiz.test

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.geoquiz.MainActivity.MainActivity
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        onView(withId(com.example.geoquiz.R.id.question_text_view))
            .check(matches(withText(com.example.geoquiz.R.string.question_australia)))
    }

    @Test
    fun nextButtonTest() {
        onView(withId(com.example.geoquiz.R.id.next_button)).perform(click())
        onView(withId(com.example.geoquiz.R.id.question_text_view))
            .check(matches(withText(com.example.geoquiz.R.string.question_oceans)))
    }

    @Test
    fun handlesActivityRecreation() {
        onView(withId(com.example.geoquiz.R.id.next_button)).perform(click())
        scenario.recreate()
        onView((withId(com.example.geoquiz.R.id.question_text_view)))
            .check(matches(withText(com.example.geoquiz.R.string.question_oceans)))

    }
}