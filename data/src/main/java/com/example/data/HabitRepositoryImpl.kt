package com.example.data

import android.util.Log
import com.example.domain.Habit
import com.example.domain.HabitDone
import com.example.domain.HabitRepository
import kotlinx.coroutines.flow.Flow

const val LOG_DEBUG = "Debug"
const val LOG_NETWORK = "Network"
const val KEY_FOR_HABIT = "habit"
const val KEY_FOR_SAVING_TYPE_FILTER = "typeFilter"
const val PASS_TYPE = "typeFilter"


class HabitRepositoryImpl(private val dataBase: HabitsDao, private val retrofit: HabitService) :
    HabitRepository {

    override suspend fun addHabit(habit: Habit) {
        dataBase.addHabit(habit)
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return dataBase.getAllHabits()
    }

    override fun getBy(uid: String): Flow<Habit> {
        return dataBase.getBy(uid)
    }

    override suspend fun deleteHabit(habit: Habit) {
        dataBase.deleteHabit(habit)
    }

    override suspend fun deleteHabits(habits: List<Habit>) {
        habits.forEach { deleteHabit(it) }
    }

    override suspend fun updateHabit(habit: Habit) {
        dataBase.updateHabit(habit)
    }

    override suspend fun updateListOfHabits(habits: List<Habit>) {
        dataBase.addHabits(habits)
    }

    override suspend fun serverGetHabits(): List<Habit> {
        val habits = retrofit.getListOfHabits()
        Log.d(LOG_NETWORK, "SERVER GET: $habits")
        return habits
    }

    override suspend fun serverPutHabit(habit: Habit): String {
        val uid = retrofit.updateHabit(habit).uid
        Log.d(LOG_NETWORK, "SERVER PUT: $uid")
        return uid
    }

    override suspend fun doneHabit(habitDone: HabitDone) {
        Log.d(LOG_DEBUG, "DONE $habitDone")
        retrofit.doneHabit(habitDone)
    }
}