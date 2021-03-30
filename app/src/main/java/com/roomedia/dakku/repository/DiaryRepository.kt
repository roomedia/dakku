package com.roomedia.dakku.repository

import com.roomedia.dakku.persistence.DakkuDatabase
import com.roomedia.dakku.persistence.Diary
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiaryRepository : CommonRepository<Diary>() {
    override val dao = DakkuDatabase.instance.diaryDao()
    val diaries = dao.getAll()

    fun deleteAll() {
        GlobalScope.launch {
            dao.deleteAll()
        }
    }
}
