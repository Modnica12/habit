package com.example.habit.uitest

import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.habit.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class UITest {

    @Rule
    @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAddHabit() {
        onScreen<HabitsListScreen> {
            floatActionButton.click()
        }

        onScreen<DataInputScreen> {
            title.replaceText("Test Title")
            description.replaceText("Test Description")
            count.replaceText("2")
            frequency.replaceText("1")
            badType.click()
            confirm.click()
        }
    }
}