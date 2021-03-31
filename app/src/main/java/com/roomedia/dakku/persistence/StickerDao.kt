package com.roomedia.dakku.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StickerDao : CommonDao<Sticker> {
    @Query("SELECT * FROM sticker WHERE diaryId = :diaryId ORDER BY id ASC")
    fun getFrom(diaryId: Long): LiveData<List<Sticker>>

    @Transaction
    fun insertInto(diary: Diary, vararg stickers: Sticker) {
        insert(diary)
        insert(*stickers)
    }

    @Insert
    fun insert(diary: Diary)

    @Query("DELETE FROM sticker WHERE diaryId = :diaryId")
    fun deleteFrom(diaryId: Long)
}
