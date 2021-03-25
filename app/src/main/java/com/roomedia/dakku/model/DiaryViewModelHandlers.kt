package com.roomedia.dakku.model

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
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

    fun onBookmark(view: View, diary: Diary) {
        toggleBookmark(view)
        diaryViewModel.onBookmark(diary)
    }

    fun onLock(view: View, diary: Diary) {
        toggleLock(view) {
            diaryViewModel.onLock(diary)
        }
    }

    fun toggleBookmark(view: View) {
        with(view) {
            isSelected = !isSelected
            contentDescription = if (isSelected) {
                R.string.bookmark_on
            } else {
                R.string.bookmark_off
            }.let { contentDescriptionId ->
                context.getString(contentDescriptionId)
            }
        }
    }

    fun toggleLock(view: View, okCompletionCallback: (() -> Unit)? = null) {
        with(view) {
            if (!isSelected) {
                isSelected = true
                contentDescription = context.getString(R.string.lock_on)
                okCompletionCallback?.invoke()
                return
            }
            showPasswordUnlockDialog(context) {
                isSelected = false
                contentDescription = context.getString(R.string.lock_off)
                okCompletionCallback?.invoke()
            }
        }
    }
}
