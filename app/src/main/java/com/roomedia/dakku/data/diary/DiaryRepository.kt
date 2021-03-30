package com.roomedia.dakku.data.diary

import android.content.Context
import com.roomedia.dakku.data.CommonRepository
import com.roomedia.dakku.util.DiaryDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiaryRepository(applicationContext: Context) : CommonRepository<Diary>() {
    override val dao = DiaryDatabase.getInstance(applicationContext).diaryDao()
    val diaries = dao.getAll()

    fun deleteAll() {
        GlobalScope.launch {
            dao.deleteAll()
        }
    }
}
