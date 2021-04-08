package com.roomedia.dakku.ui.editor

import android.widget.FrameLayout
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class AddMenuHandlers(
    private val activity: DiaryEditorActivity,
    private val frame: FrameLayout
) {
    fun onText() {
        StickerTextViewImpl(activity).apply {
            activity.selectSticker(this)
            showEditTextDialog()
        }.also {
            frame.addView(it)
        }
    }

    fun onImage() {
        StickerImageViewImpl(activity).apply {
            activity.selectSticker(this)
            showSelectItemDialog()
        }.also {
            frame.addView(it)
        }
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
