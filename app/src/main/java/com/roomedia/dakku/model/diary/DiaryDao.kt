package com.roomedia.dakku.model.diary

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.roomedia.dakku.data.diary.Diary
import com.roomedia.dakku.model.CommonDao

@Dao
abstract class DiaryDao : CommonDao<Diary> {
    @Query("SELECT * FROM diary ORDER BY date ASC")
    abstract fun getAll(): LiveData<List<Diary>>

    @Query("DELETE FROM diary")
    abstract fun deleteAll()
}
