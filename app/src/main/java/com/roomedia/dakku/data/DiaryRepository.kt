package com.roomedia.dakku.data

import android.content.Context
import com.roomedia.dakku.util.DiaryDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiaryRepository(applicationContext: Context) {
    private val diaryDao = DiaryDatabase.getInstance(applicationContext).diaryDao()
    val diaries = diaryDao.getAll()

    fun insert(vararg diaries: Diary) {
        GlobalScope.launch {
            diaryDao.insert(*diaries)
        }
    }

    fun update(vararg diaries: Diary) {
        GlobalScope.launch {
            diaryDao.update(*diaries)
        }
    }

    fun delete(vararg diaries: Diary) {
        GlobalScope.launch {
            diaryDao.delete(*diaries)
        }
    }
    fun deleteAll() {
        GlobalScope.launch {
            diaryDao.deleteAll()
        }
    }
}
