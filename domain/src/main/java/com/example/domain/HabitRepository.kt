package com.example.domain

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addHabit(habit: Habit)

    fun getAllHabits(): Flow<List<Habit>>

    fun getBy(uid: String): Flow<Habit>

    suspend fun deleteHabit(habit: Habit)

    suspend fun deleteHabits(habits: List<Habit>)

    suspend fun updateHabit(habit: Habit)

    suspend fun updateListOfHabits(habits: List<Habit>)

    suspend fun serverGetHabits(): List<Habit>

    suspend fun serverPutHabit(habit: Habit): String

    suspend fun doneHabit(habitDone: HabitDone)
}