package com.gangulwar.habitheat.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_completions",
    indices = [Index(value = ["habitId", "date"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = Habit::class,
        parentColumns = ["id"],
        childColumns = ["habitId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HabitCompletion(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val habitId: Long,
    val date: String,
    val isCompleted: Boolean,
    val note: String? = null,
    val entryTime: Long = System.currentTimeMillis(),
    val progressStatus: String = ProgressStatus.NEUTRAL.status
)

enum class ProgressStatus(val status: String) {
    PRODUCTIVE("Productive"),
    NEUTRAL("Neutral"),
    MISSED("Missed");

    companion object {
        fun fromString(value: String): ProgressStatus {
            return entries.firstOrNull { it.status == value } ?: NEUTRAL
        }
    }
}
