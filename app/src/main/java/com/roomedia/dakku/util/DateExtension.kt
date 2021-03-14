package com.roomedia.dakku.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

private fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().also {
        it.time = this
    }
}

fun Date.isInSameWeek(that: Date): Boolean {
    return this.toCalendar().get(Calendar.WEEK_OF_YEAR) ==
        that.toCalendar().get(Calendar.WEEK_OF_YEAR)
}

fun Date.toWeekString(localeDateFormat: SimpleDateFormat): String {
    val first = toCalendar().apply {
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
    val last = (first.clone() as Calendar).apply {
        add(Calendar.DATE, 6)
    }
    return String.format("%s - %s", localeDateFormat.format(first.time), localeDateFormat.format(last.time))
}

fun List<Date>.splitByWeek(): List<List<Date>> {
    return mutableListOf<MutableList<Date>>().also { weeks ->
        forEach { date ->
            if (weeks.isNotEmpty() && weeks.last().last().isInSameWeek(date)) {
                weeks.last().add(date)
            } else {
                weeks.add(mutableListOf(date))
            }
        }
    }
}
