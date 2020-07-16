package com.example.domain.usecases

import com.example.domain.Habit
import com.example.domain.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetHabitsUseCase(
    private val repository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    @ExperimentalCoroutinesApi
    fun getHabits(): Flow<List<Habit>> = repository.getAllHabits().flowOn(dispatcher)
}