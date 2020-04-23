package com.example.habit

import retrofit2.http.*

interface HabitService {
    @Headers("Authorization: e418cfbf-fbbb-409c-b392-e39c0ea67b63")
    @GET("habit")
    suspend fun getListOfHabits():List<Habit>

    @Headers("Authorization: e418cfbf-fbbb-409c-b392-e39c0ea67b63")
    @PUT("habit")
    suspend fun updateHabit(@Body habit: Habit): HabitUID

    @Headers("Authorization: e418cfbf-fbbb-409c-b392-e39c0ea67b63")
    @DELETE("habit")
    suspend fun deleteHabit(@Body uid: HabitUID)
}