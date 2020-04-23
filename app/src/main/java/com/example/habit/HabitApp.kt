package com.example.habit

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.habit.fragments.LOG_DEBUG

class HabitApp : Application() {
    companion object {
        lateinit var instance: HabitApp
        private lateinit var dataBase: AppDataBase
        var dataBaseSize: Int = 0
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBase = Room.databaseBuilder(this,
            AppDataBase::class.java,
            "habit-app-database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getInstance() = instance

    fun getDataBase() = dataBase
}