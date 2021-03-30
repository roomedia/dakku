package com.roomedia.dakku.model.diary

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.roomedia.dakku.data.diary.Diary
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.util.REQUEST
import com.roomedia.dakku.util.showPasswordOpenDialog
import com.roomedia.dakku.util.showPasswordUnlockDialog

class DiaryViewModelHandlers {

    private val diaryViewModel = ViewModelProvider.AndroidViewModelFactory(Application())
        .create(DiaryViewModel::class.java)

    fun onThumbnail(context: Context, diary: Diary) {
        if (!diary.lock) {
            val intent = Intent(context, DiaryEditorActivity::class.java).apply {
                putExtra("request_code", REQUEST.EDIT_DIARY.ordinal)
                putExtra("diary_id", diary.id)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
            return
        }
        showPasswordOpenDialog(context) {
            val intent = Intent(context, DiaryEditorActivity::class.java).apply {
                putExtra("request_code", REQUEST.EDIT_DIARY.ordinal)
                putExtra("diary_id", diary.id)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
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
