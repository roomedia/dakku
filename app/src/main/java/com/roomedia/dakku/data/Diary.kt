package com.roomedia.dakku.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class Diary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val title: String = date.toString(),
    var bookmark: Boolean = false,
    var lock: Boolean = false
) {
    fun toCalendar(): Calendar = Calendar.getInstance().also {
        it.time = Date(date)
    }

    fun isInSameWeek(that: Diary): Boolean {
        return this.toCalendar().get(Calendar.WEEK_OF_YEAR) ==
            that.toCalendar().get(Calendar.WEEK_OF_YEAR)
    }
}
