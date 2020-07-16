package com.example.data

import com.example.domain.Habit
import com.example.domain.HabitDone
import com.example.domain.HabitUID
import retrofit2.http.*

private const val authToken = "Authorization: e418cfbf-fbbb-409c-b392-e39c0ea67b63"
private const val habitPath = "habit"
private const val doneHabitPath = "habit_done"

interface HabitService {
    @Headers(authToken)
    @GET(habitPath)
    suspend fun getListOfHabits():List<Habit>

    @Headers(authToken)
    @PUT(habitPath)
    suspend fun updateHabit(@Body habit: Habit): HabitUID

    @Headers(authToken)
    @DELETE(habitPath)
    suspend fun deleteHabit(@Body uid: HabitUID)

    @Headers(authToken)
    @POST(doneHabitPath)
    suspend fun doneHabit(@Body habitDone: HabitDone)
}