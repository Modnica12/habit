package com.example.domain.usecases

import android.util.Log
import com.example.domain.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SaveHabitsFromServerUseCase(
    private val repository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend fun saveHabitsFromServer(){
        withContext(dispatcher){
            val habits = repository.serverGetHabits()
            repository.updateListOfHabits(habits)
        }
    }
}