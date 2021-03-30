package com.roomedia.dakku.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class StickerDao : CommonDao<Sticker> {
    @Query("SELECT * FROM sticker WHERE diaryId = :diaryId ORDER BY id ASC")
    abstract fun getFrom(diaryId: Int): LiveData<List<Sticker>>

    @Query("DELETE FROM sticker WHERE diaryId = :diaryId")
    abstract fun deleteFrom(diaryId: Int)
}
