package com.roomedia.dakku.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class Diary(
    @PrimaryKey val id: Long = Date().time,
    val title: String = id.toString(),
    var bookmark: Boolean = false,
    var lock: Boolean = false,
) {
    fun toCalendar(): Calendar = Calendar.getInstance().also {
        it.time = Date(id)
    }

    fun isInSameWeek(that: Diary): Boolean {
        return this.toCalendar().get(Calendar.WEEK_OF_YEAR) ==
            that.toCalendar().get(Calendar.WEEK_OF_YEAR)
    }
}
