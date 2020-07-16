package com.example.domain.usecases

import com.example.domain.DoneHabitToastType

class GetDoneHabitToastTypeUseCase {
    fun getDoneHabitToastType(isExceeded: Boolean, type: Int) =
        if (isExceeded)
            when (type){
                0 -> DoneHabitToastType.BAD_EXCEED
                1 -> DoneHabitToastType.GOOD_EXCEED
                else -> DoneHabitToastType.UNKNOWN
            }
        else
            when (type){
                0 -> DoneHabitToastType.BAD_LEFT
                1 -> DoneHabitToastType.GOOD_LEFT
                else -> DoneHabitToastType.UNKNOWN
            }
}