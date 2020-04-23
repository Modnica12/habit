package com.example.habit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habit.fragments.LOG_DEBUG
import com.example.habit.fragments.LOG_NETWORK
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

typealias subscriber = (ArrayList<Habit>) -> Unit

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): LiveData<List<Habit>>

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE uid=:uid ")
    fun getBy(uid: String): LiveData<Habit>
}

@Database(entities = [Habit::class], version = 7)
abstract class AppDataBase: RoomDatabase(){
    abstract fun habitsDao(): HabitsDao
}

object HabitsData {

    private val dataBase = HabitApp.instance.getDataBase().habitsDao()

    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HabitService::class.java)

    fun addHabit(habit: Habit){
        dataBase.addHabit(habit)
    }

    fun getAllHabits(): LiveData<List<Habit>> {
        return dataBase.getAllHabits()
    }

    fun getBy(uid: String): LiveData<Habit>{
        return dataBase.getBy(uid)
    }

    fun deleteHabit(habit: Habit){
        dataBase.deleteHabit(habit)
    }

    fun updateHabit(habit: Habit){
        dataBase.updateHabit(habit)
    }

    fun updateListOfHabits(habits: ArrayList<Habit>){
        habits.map{
            dataBase.updateHabit(it)
        }
    }

    suspend fun serverGet(): ArrayList<Habit>{
        val habits = retrofit.getListOfHabits()
        Log.d(LOG_NETWORK, "SERVER GET: $habits")
        return habits as ArrayList<Habit>
    }

    suspend fun serverPut(habit: Habit): String{
        val uid = retrofit.updateHabit(habit).uid
        Log.d(LOG_NETWORK, "SERVER PUT: $uid")
        return uid
    }

}