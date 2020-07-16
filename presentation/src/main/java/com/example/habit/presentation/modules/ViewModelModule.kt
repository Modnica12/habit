package com.example.habit.presentation.modules

import androidx.lifecycle.ViewModel
import com.example.habit.presentation.ViewModelKey
import com.example.habit.presentation.viewmodels.DataInputViewModel
import com.example.habit.presentation.viewmodels.HabitsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DataInputViewModel::class)
    internal abstract fun dataInputViewModel(dataInputViewModel: DataInputViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HabitsListViewModel::class)
    internal abstract fun habitsListViewModel(habitsListViewModel: HabitsListViewModel): ViewModel
}