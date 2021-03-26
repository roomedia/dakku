package com.roomedia.dakku.model.diary

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.roomedia.dakku.data.diary.Diary
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.util.showPasswordOpenDialog
import com.roomedia.dakku.util.showPasswordUnlockDialog

class DiaryViewModelHandlers {

    private val diaryViewModel = ViewModelProvider.AndroidViewModelFactory(Application())
        .create(DiaryViewModel::class.java)

    fun onThumbnail(context: Context, isLocked: Boolean) {
        if (!isLocked) {
            context.startActivity(Intent(context, DiaryEditorActivity::class.java))
            return
        }
        showPasswordOpenDialog(context) {
            context.startActivity(Intent(context, DiaryEditorActivity::class.java))
        }
    }

    fun onBookmark(diary: Diary) {
        diaryViewModel.onBookmark(diary)
    }

    fun onLock(context: Context, diary: Diary) {
        if (!diary.lock) {
            diaryViewModel.onLock(diary)
            return
        }
        showPasswordUnlockDialog(context) {
            diaryViewModel.onLock(diary)
        }
    }
}
