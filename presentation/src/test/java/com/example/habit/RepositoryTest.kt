package com.example.habit

import com.example.data.HabitRepositoryImpl
import com.example.data.HabitService
import com.example.data.HabitsDao
import com.example.domain.Habit
import com.example.domain.HabitDone
import com.example.domain.HabitUID
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class RepositoryTest {
    private val service: HabitService = mock()
    private val dao: HabitsDao = mock()
    private val repository = HabitRepositoryImpl(dao, service)

    @Before
    fun setUpTest(){
        runBlockingTest {
            whenever(service.updateHabit(any())).thenReturn(HabitUID("uid"))
            whenever(service.getListOfHabits()).thenReturn(listOf(
                Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
                Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
                Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
            ))
            whenever(dao.getAllHabits()).thenReturn(
                flowOf(listOf(
                    Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
                    Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
                    Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
                ))
            )
            whenever(dao.getBy("uid1")).thenReturn(flowOf(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1")))
        }
    }

    @Test
    fun testPutHabit() = runBlockingTest {
        val uid = repository.serverPutHabit(Habit(1, 1, 1, "description", listOf(), 1, 1, "title", 1, ""))
        assertEquals("uid", uid)
    }

    @Test
    fun testSaveHabit() = runBlockingTest {
        val result = repository.addHabit(Habit(4, 4, 4, "description4", listOf(), 4, 4, "title4", 4, "uid4"))
        assertEquals(Unit, result)
    }

    @Test
    fun testUpdateHabits() = runBlockingTest {
        val result = repository.updateListOfHabits(listOf(
            Habit(4, 4, 4, "description4", listOf(), 4, 4, "title4", 4, "uid4"),
            Habit(5, 5, 5, "description5", listOf(), 5, 5, "title5", 5, "uid5")))
        assertEquals(Unit, result)
    }

    @Test
    fun testUpdateHabit() = runBlockingTest {
        val result = repository.updateHabit(Habit(4, 4, 4, "description4", listOf(), 4, 4, "title4", 4, "uid4"))
        assertEquals(Unit, result)
    }

    @Test
    fun testGetHabits() = runBlockingTest {
        val habits = repository.getAllHabits().first()
        assertEquals(listOf(
            Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
            Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
            Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
        ), habits)
    }

    @Test
    fun testGetHabit() = runBlockingTest {
        val habit = repository.getBy("uid1").first()
        assertEquals(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"), habit)
    }

    @Test
    fun testDeleteHabit() = runBlockingTest {
        val result = repository.deleteHabit(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"))
        assertEquals(Unit, result)
    }

    @Test
    fun testDeleteHabits() = runBlockingTest {
        val result = repository.deleteHabits(listOf(Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1")))
        assertEquals(Unit, result)
    }

    @Test
    fun testGetHabitsFromServer() = runBlockingTest {
        val habits = repository.serverGetHabits()
        assertEquals(listOf(
            Habit(1, 1, 1, "description1", listOf(), 1, 1, "title1", 1, "uid1"),
            Habit(2, 2, 2, "description2", listOf(), 2, 2, "title2", 2, "uid2"),
            Habit(3, 3, 3, "description3", listOf(), 3, 3, "title3", 3, "uid3")
        ), habits)
    }

    @Test
    fun testDoneHabit() = runBlockingTest {
        val result = repository.doneHabit(HabitDone(1, "uid1"))
        assertEquals(Unit, result)
    }
}