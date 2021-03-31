package com.roomedia.dakku.ui.list

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableBoolean
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.repository.DiaryRepository
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.util.CommonViewModel
import com.roomedia.dakku.ui.util.REQUEST
import com.roomedia.dakku.ui.util.showPasswordOpenDialog
import com.roomedia.dakku.ui.util.showPasswordUnlockDialog

class DiaryViewModel(private val diary: Diary) : CommonViewModel<Diary>() {
    override val repository = DiaryRepository()
    val isBookmarked = ObservableBoolean(diary.bookmark)
    val isLocked = ObservableBoolean(diary.lock)
    val title = diary.title

    fun onThumbnail(context: Context) {
        val intent = Intent(context, DiaryEditorActivity::class.java).apply {
            putExtra("request_code", REQUEST.EDIT_DIARY.ordinal)
            putExtra("diary_id", diary.id)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        if (!diary.lock) {
            startActivity(context, intent, null)
            return
        }
        showPasswordOpenDialog(context) {
            startActivity(context, intent, null)
        }
    }

    fun onBookmark() {
        diary.bookmark = !diary.bookmark
        isBookmarked.set(diary.bookmark)
    }

    fun onLock(context: Context) {
        if (!diary.lock) {
            diary.lock = !diary.lock
            isLocked.set(diary.lock)
            return
        }
        showPasswordUnlockDialog(context) {
            diary.lock = !diary.lock
            isLocked.set(diary.lock)
        }
    }
}
