package com.roomedia.dakku.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class DiaryDao : CommonDao<Diary> {
    @Query("SELECT * FROM diary ORDER BY date ASC")
    abstract fun getAll(): LiveData<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    abstract fun getDiary(id: Int): LiveData<Diary>

    @Query("DELETE FROM diary")
    abstract fun deleteAll()
}
