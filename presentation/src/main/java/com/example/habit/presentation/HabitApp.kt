package com.example.habit.presentation

import android.app.Application
import com.example.habit.presentation.modules.ContextModule

class HabitApp : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().contextModule(
            ContextModule(this)
        ).build()
    }
}