package com.roomedia.dakku.persistence

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["diaryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Diary::class,
            parentColumns = ["id"],
            childColumns = ["diaryId"]
        ),
    ],
)
data class Sticker(
    val diaryId: Long,
    val x: Float,
    val y: Float,
    val w: Float,
    val h: Float,
    val rot: Float,
    val zIndex: Int,
    val text: String?,
    var type: StickerType? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
