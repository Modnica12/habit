package com.example.habit.presentation

import com.example.habit.presentation.fragments.BottomSheetFragment
import com.example.habit.presentation.fragments.DataInputFragment
import com.example.habit.presentation.fragments.HabitsListFragment
import com.example.habit.presentation.modules.ContextModule
import com.example.habit.presentation.modules.HabitsModule
import com.example.habit.presentation.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsModule::class, ContextModule::class, ViewModelModule::class])
interface ApplicationComponent{
    fun inject(habitsListFragment: HabitsListFragment)
    fun inject(dataInputFragment: DataInputFragment)
    fun inject(bottomSheetFragment: BottomSheetFragment)
}