package com.roomedia.dakku.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.roomedia.dakku.data.Diary

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date ASC")
    fun getAll(): LiveData<List<Diary>>

    @Insert
    fun insert(vararg diaries: Diary)

    @Update
    fun update(vararg diaries: Diary)

    @Delete
    fun delete(vararg diaries: Diary)

    @Query("DELETE FROM diary")
    fun deleteAll()
}
