package com.roomedia.dakku.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.data.DiaryRepository

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiaryRepository(application)
    val diaries = repository.diaries

    fun insert(vararg diaries: Diary) {
        repository.insert(*diaries)
    }

    fun update(vararg diaries: Diary) {
        repository.update(*diaries)
    }

    fun delete(vararg diaries: Diary) {
        repository.delete(*diaries)
    }

    fun deleteAll() {
        repository.deleteAll()
    }
}
