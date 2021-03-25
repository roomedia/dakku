package com.roomedia.dakku.util

import android.view.MenuItem
import androidx.annotation.DrawableRes
import com.roomedia.dakku.data.Diary
import java.text.SimpleDateFormat
import java.util.Calendar

fun MenuItem.setIcon(@DrawableRes iconIdOn: Int, @DrawableRes iconIdOff: Int): MenuItem {
    val iconId = if (isChecked) iconIdOn else iconIdOff
    setIcon(iconId)
    return this
}

fun List<Diary>.splitByWeek(): List<List<Diary>> {
    return mutableListOf<MutableList<Diary>>().also { weeks ->
        forEach { diary ->
            if (weeks.isNotEmpty() && weeks.last().last().isInSameWeek(diary)) {
                weeks.last().add(diary)
            } else {
                weeks.add(mutableListOf(diary))
            }
        }
    }
}

fun List<Diary>.filterBookmark(isChecked: Boolean): List<Diary> {
    return if (isChecked) this.filter { it.bookmark } else this
}

fun List<Diary>.filterLock(isChecked: Boolean): List<Diary> {
    return if (isChecked) this.filter { it.lock } else this
}

fun List<Diary>.toWeekString(localeDateFormat: SimpleDateFormat): String {
    val first = first().toCalendar().apply {
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
    val last = (first.clone() as Calendar).apply {
        add(Calendar.DATE, 6)
    }
    return String.format(
        "%s - %s",
        localeDateFormat.format(first.time),
        localeDateFormat.format(last.time)
    )
}
