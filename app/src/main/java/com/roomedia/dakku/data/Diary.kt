package com.roomedia.dakku.data

import java.util.Calendar
import java.util.Date

data class Diary(
    val date: Date,
    val title: String = date.toString(),
    var bookmark: Boolean = false,
    var lock: Boolean = false
) {

    val calendar: Calendar = Calendar.getInstance().also {
        it.time = date
    }

    fun isInSameWeek(that: Diary): Boolean {
        return this.calendar.get(Calendar.WEEK_OF_YEAR) ==
            that.calendar.get(Calendar.WEEK_OF_YEAR)
    }
}
