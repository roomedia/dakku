package com.roomedia.dakku.util

import com.roomedia.dakku.data.Diary

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
