package com.roomedia.dakku.model.diary

import android.app.Application
import com.roomedia.dakku.data.diary.Diary
import com.roomedia.dakku.data.diary.DiaryRepository
import com.roomedia.dakku.model.CommonViewModel

class DiaryViewModel(application: Application) : CommonViewModel<Diary>(application) {
    override val repository = DiaryRepository(application)
    val diaries = repository.diaries

    fun deleteAll() {
        repository.deleteAll()
    }

    fun onBookmark(diary: Diary) {
        diary.bookmark = !diary.bookmark
        update(diary)
    }

    fun onLock(diary: Diary) {
        diary.lock = !diary.lock
        update(diary)
    }
}
