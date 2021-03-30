package com.roomedia.dakku.data.sticker

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.roomedia.dakku.data.diary.Diary

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Diary::class,
            parentColumns = ["id"],
            childColumns = ["diaryId"]
        ),
    ]
)
data class Sticker(
    val diaryId: Int,
    val x: Float,
    val y: Float,
    val w: Float,
    val h: Float,
    val rot: Float,
    val zIndex: Int,
    val text: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
