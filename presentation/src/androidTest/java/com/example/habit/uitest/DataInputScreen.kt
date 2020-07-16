package com.example.habit.uitest

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.screen.Screen
import com.example.habit.R

class DataInputScreen: Screen<DataInputScreen>() {
    val title = KEditText {
        withId(R.id.enterName)
    }

    val description = KEditText {
        withId(R.id.enterDescription)
    }

    val spinner = KView {
        withId(R.id.prioritiesSpinner)
    }

    val count = KEditText {
        withId(R.id.enterQuantity)
    }

    val frequency = KEditText {
        withId(R.id.enterPeriod)
    }

    val badType = KView {
        withId(R.id.badType)
    }

    val confirm = KView {
        withId(R.id.confirmHabitButton)
    }
}