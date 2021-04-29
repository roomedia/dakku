package com.roomedia.dakku.ui.editor.menu

import android.view.View
import android.widget.FrameLayout
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class AddMenuBar(
    private val menuHandlersManager: MenuHandlersManager,
    private val diaryFrame: FrameLayout
) {
    fun onText(view: View) {
        menuHandlersManager.selectMenu(view.id)
        StickerTextViewImpl(menuHandlersManager).apply {
            menuHandlersManager.selectSticker(this)
            showEditTextDialog()
        }.also {
            diaryFrame.addView(it)
        }
    }

    fun onImage(view: View) {
        menuHandlersManager.selectMenu(view.id)
        StickerImageViewImpl(menuHandlersManager).apply {
            menuHandlersManager.selectSticker(this)
            showSelectItemDialog()
        }.also {
            diaryFrame.addView(it)
        }
    }

    fun onVideo(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }

    fun onSwitch(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }

    fun onRadio(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }

    fun onCheck(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }

    fun onSlider(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }
}
