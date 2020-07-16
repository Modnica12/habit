package com.example.habit.uitest

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.example.habit.R

class HabitsListScreen: Screen<HabitsListScreen>() {
    val floatActionButton = KView{
        withId(R.id.addHabitButton)
    }
}