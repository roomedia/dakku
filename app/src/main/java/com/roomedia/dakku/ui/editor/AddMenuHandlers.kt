package com.roomedia.dakku.ui.editor

import android.widget.FrameLayout
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class AddMenuHandlers(private val frame: FrameLayout) {
    fun onText() {
        StickerTextViewImpl(frame.context).apply {
            showEditTextDialog()
        }.also {
            frame.addView(it)
        }
    }

    fun onImage() {
        StickerImageViewImpl(frame.context).apply {
            showSelectImageDialog()
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
