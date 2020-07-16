package com.example.domain.usecases

import com.example.domain.Habit
import com.example.domain.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SaveHabitUseCase(private val repository: HabitRepository,
                       private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateHabit(habit: Habit, isNew: Boolean){
        withContext(dispatcher){
            val uid = repository.serverPutHabit(habit)
            if(isNew) {
                habit.uid = uid
                repository.addHabit(habit)
            }
            else repository.updateHabit(habit)
        }
    }
}
