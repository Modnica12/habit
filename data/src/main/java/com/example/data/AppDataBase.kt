package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.Habit
import com.example.domain.HabitDoneConverter

@Database(entities = [Habit::class], version = 8)
@TypeConverters(HabitDoneConverter::class)
abstract class AppDataBase: RoomDatabase(){
    abstract fun habitsDao(): HabitsDao
}