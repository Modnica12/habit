package com.example.habit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.Habit
import com.example.domain.HabitRepository
import com.example.domain.usecases.DoneHabitUseCase
import com.example.domain.usecases.GetDoneHabitToastTypeUseCase
import com.example.domain.usecases.GetHabitsUseCase
import com.example.domain.usecases.SaveHabitsFromServerUseCase
import com.example.habit.presentation.viewmodels.HabitsListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HabitsListViewModelTest {
    private val repository: HabitRepository = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val viewModel = HabitsListViewModel(
        GetHabitsUseCase(repository, testDispatcher),
        SaveHabitsFromServerUseCase(repository, testDispatcher),
        DoneHabitUseCase(repository, testDispatcher),
        GetDoneHabitToastTypeUseCase())

    @Before
    fun setUpTest() = runBlockingTest {
        whenever(repository.getAllHabits()).thenReturn(
            flowOf(listOf(
                Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
                Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
                Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
            ))
        )
    }

    @Test
    fun testGetAllHabits() = runBlockingTest {
        val habits = viewModel.getAllHabits().value
        assertEquals(listOf(
            Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
            Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
            Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
            ), habits)
    }
}