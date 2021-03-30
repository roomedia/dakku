package com.roomedia.dakku.model.sticker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.roomedia.dakku.data.sticker.Sticker
import com.roomedia.dakku.model.CommonDao

@Dao
abstract class StickerDao : CommonDao<Sticker> {
    @Query("SELECT * FROM sticker WHERE diaryId = :diaryId ORDER BY id ASC")
    abstract fun getFrom(diaryId: Int): LiveData<List<Sticker>>

    @Query("DELETE FROM sticker WHERE diaryId = :diaryId")
    abstract fun deleteFrom(diaryId: Int)
}
