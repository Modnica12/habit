package com.example.habit

import androidx.lifecycle.LiveData
import androidx.room.*

typealias subscriber = (ArrayList<Habit>) -> Unit

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)
}

@Database(entities = [Habit::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun habitsDao(): HabitsDao
}