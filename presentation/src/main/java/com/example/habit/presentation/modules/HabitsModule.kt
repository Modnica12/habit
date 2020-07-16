package com.example.habit.presentation.modules

import android.content.Context
import androidx.room.Room
import com.example.data.AppDataBase
import com.example.data.HabitRepositoryImpl
import com.example.data.HabitService
import com.example.data.HabitsDao
import com.example.domain.HabitRepository
import com.example.domain.usecases.*
import com.example.habit.R
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class HabitsModule {

    @Singleton
    @Provides
    fun provideHabitDataBase(context: Context): HabitsDao {
        return Room.databaseBuilder(context,
            AppDataBase::class.java,
            "habit-app-database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
            .habitsDao()
    }

    @Singleton
    @Provides
    fun provideRetrofit(context: Context): HabitService {
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HabitService::class.java)
    }

    @Singleton
    @Provides
    fun provideHabitRepository(habitService: HabitService, habitsDao: HabitsDao): HabitRepository{
        return HabitRepositoryImpl(habitsDao, habitService)
    }

    @Singleton
    @Provides
    fun provideGetHabitsUseCase(repository: HabitRepository): GetHabitsUseCase{
        return GetHabitsUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideGetHabitUseCase(repository: HabitRepository): GetHabitUseCase {
        return GetHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideSaveHabitsFromServerUseCase(repository: HabitRepository): SaveHabitsFromServerUseCase{
        return SaveHabitsFromServerUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideSaveHabitUseCase(repository: HabitRepository): SaveHabitUseCase{
        return SaveHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDoneHabitUseCase(repository: HabitRepository): DoneHabitUseCase{
        return DoneHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideGetToastContentTypeUseCase(): GetDoneHabitToastTypeUseCase{
        return GetDoneHabitToastTypeUseCase()
    }
}