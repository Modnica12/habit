package com.example.data

import androidx.room.*
import com.example.domain.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert
    suspend fun addHabit(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabits(habits: List<Habit>)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE uid=:uid ")
    fun getBy(uid: String): Flow<Habit>
}

