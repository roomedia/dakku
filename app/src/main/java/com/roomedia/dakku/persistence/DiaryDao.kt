package com.roomedia.dakku.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface DiaryDao : CommonDao<Diary> {
    @Query("SELECT * FROM diary ORDER BY id DESC")
    fun getAll(): LiveData<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    fun getDiary(id: Int): LiveData<Diary>

    @Query("DELETE FROM diary")
    fun deleteAll()
}
