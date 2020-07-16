package com.example.habit.presentation.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {
    @Provides
    fun provideContext() = context
}