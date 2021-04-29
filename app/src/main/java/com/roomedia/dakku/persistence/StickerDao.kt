package com.roomedia.dakku.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StickerDao : CommonDao<Sticker> {
    @Query("SELECT * FROM sticker WHERE diaryId = :diaryId ORDER BY zIndex ASC")
    fun getFrom(diaryId: Long): LiveData<List<Sticker>>

    @Transaction
    fun insertInto(diary: Diary, vararg stickers: Sticker) {
        insert(diary)
        insert(*stickers)
    }

    @Insert(onConflict = REPLACE)
    fun insert(diary: Diary)

    @Insert(onConflict = REPLACE)
    override fun insert(vararg entities: Sticker)

    @Query("DELETE FROM sticker WHERE diaryId = :diaryId")
    fun deleteFrom(diaryId: Long)
}
