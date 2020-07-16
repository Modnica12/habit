package com.example.habit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.DoneHabitToastType
import com.example.domain.Habit
import com.example.domain.HabitRepository
import com.example.domain.usecases.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class InteractorTest {
    private val repository: HabitRepository = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUpTest() = runBlockingTest {
            whenever(repository.getAllHabits()).thenReturn(
                flowOf(listOf(
                    Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
                    Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
                    Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
                ))
            )
            whenever(repository.getBy("uid1")).thenReturn(flowOf(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1")))
            whenever(repository.serverPutHabit(any())).thenReturn("uidPut")
        }

    @Test
    fun testGetHabitsUseCase() = runBlockingTest {
        val habits = GetHabitsUseCase(repository, testDispatcher).getHabits().first()
        assertEquals(listOf(
            Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
            Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
            Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
        ), habits)
    }

    @Test
    fun testGetHabitUseCase() = runBlockingTest {
        val habit = GetHabitUseCase(repository, testDispatcher).getHabit("uid1").first()
        assertEquals(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"), habit)
    }

    @Test
    fun testSaveHabitUseCase() = runBlockingTest {
        val result1 = SaveHabitUseCase(repository, testDispatcher).updateHabit(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"), false)
        val result2 = SaveHabitUseCase(repository, testDispatcher).updateHabit(Habit(4, 4, 4, "description4", listOf(), 4, 4, "title4", 4, "uid4"), true)
        assertEquals(Unit, result1)
        assertEquals(Unit, result2)
    }

    @Test
    fun testDoneHabitUseCase() = runBlockingTest {
        val result = DoneHabitUseCase(repository, testDispatcher).doneHabit(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"))
        assertEquals(Unit, result)
    }

    @Test
    fun testSaveHabitsFromServerUseCase() = runBlockingTest {
        val result = SaveHabitsFromServerUseCase(repository, testDispatcher).saveHabitsFromServer()
        assertEquals(Unit, result)
    }

    @Test
    fun testGetDoneHabitToastTypeUseCase() = runBlockingTest {
        val getDoneHabitToastTypeUseCase = GetDoneHabitToastTypeUseCase()
        assertEquals(DoneHabitToastType.BAD_EXCEED, getDoneHabitToastTypeUseCase.getDoneHabitToastType(true, 0))
        assertEquals(DoneHabitToastType.BAD_LEFT, getDoneHabitToastTypeUseCase.getDoneHabitToastType(false, 0))
        assertEquals(DoneHabitToastType.GOOD_EXCEED, getDoneHabitToastTypeUseCase.getDoneHabitToastType(true, 1))
        assertEquals( DoneHabitToastType.GOOD_LEFT, getDoneHabitToastTypeUseCase.getDoneHabitToastType(false, 1))
    }
}