package com.roomedia.dakku.ui.editor

import android.widget.FrameLayout

class AddMenuHandlers(
    private val diaryEditorActivity: DiaryEditorActivity,
    private val frame: FrameLayout
) {
    fun onText() {
        StickerTextViewImpl(diaryEditorActivity).apply {
            showEditTextDialog()
        }.also {
            frame.addView(it)
        }
    }

    fun onImage() {
        TODO("not yet implemented")
    }

    fun onVideo() {
        TODO("not yet implemented")
    }

    fun onSwitch() {
        TODO("not yet implemented")
    }

    fun onRadio() {
        TODO("not yet implemented")
    }

    fun onCheck() {
        TODO("not yet implemented")
    }

    fun onSlider() {
        TODO("not yet implemented")
    }
}
