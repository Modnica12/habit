package com.example.domain
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "habits")
data class Habit(
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo var date: Int,
    @ColumnInfo val description: String?,
    @ColumnInfo var done_dates: List<Int>,
    @ColumnInfo val frequency: Int,
    @ColumnInfo val priority: Int,
    @ColumnInfo val title: String?,
    @ColumnInfo val type: Int,
    @PrimaryKey var uid: String
):Serializable {
    companion object{
        fun empty(): Habit = Habit(
            -1,
        -1,
        -1,
        null,
        listOf(),
        -1,
        -1,
        null,
        -1,
        "")

        const val dayInSeconds = 60 * 60 * 60
    }

    fun isExceeded(): Boolean{
        return getRest() <= 0
    }

    fun getRest(): Int {
        val currentTime = (Date().time / 1000).toInt()
        val periodStartTime = currentTime - dayInSeconds * frequency
        val diff = count - done_dates.count { it >= periodStartTime }
        return diff
    }
}

data class HabitUID(
    val uid: String
):Serializable
