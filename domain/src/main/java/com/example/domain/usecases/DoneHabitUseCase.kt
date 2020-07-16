package com.example.domain.usecases

import android.util.Log
import com.example.domain.Habit
import com.example.domain.HabitDone
import com.example.domain.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*

class DoneHabitUseCase(private val repository: HabitRepository,
                       private val dispatcher: CoroutineDispatcher
) {
    suspend fun doneHabit(habit: Habit){
        withContext(dispatcher){
            val date = (Date().time / 1000).toInt()
            val dates = mutableListOf<Int>()
            dates.addAll(habit.done_dates)
            dates.add(date)
            habit.done_dates = dates
            Log.d("debug", "DATES $dates")
            Log.d("debug", "In Habit Dates ${habit.done_dates}")
            repository.doneHabit(HabitDone(date, habit.uid))
            repository.updateHabit(habit)
        }
    }
}