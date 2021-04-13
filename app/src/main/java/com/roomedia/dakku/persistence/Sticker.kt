package com.roomedia.dakku.persistence

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["diaryId"])],
    foreignKeys = [
        ForeignKey(
            entity = Diary::class,
            parentColumns = ["id"],
            childColumns = ["diaryId"],
            onDelete = CASCADE,
        ),
    ],
)
data class Sticker(
    @PrimaryKey val id: Int,
    val diaryId: Long,
    val x: Float,
    val y: Float,
    val w: Int,
    val h: Int,
    val rot: Float,
    val zIndex: Int,
    var type: StickerType? = null,
    var text: CharSequence? = null,
    var textAlignment: Int? = null,
    var uri: Uri? = null,
)
