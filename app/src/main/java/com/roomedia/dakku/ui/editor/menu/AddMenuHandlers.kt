package com.roomedia.dakku.ui.editor.menu

import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class AddMenuHandlers(
    private val activity: DiaryEditorActivity,
    private val frame: FrameLayout,
    private val selectedMenu: MutableLiveData<Int>,
) {
    fun onText(view: View) {
        selectedMenu.value = view.id
        StickerTextViewImpl(activity).apply {
            activity.selectSticker(this)
            showEditTextDialog()
        }.also {
            frame.addView(it)
        }
    }

    fun onImage(view: View) {
        selectedMenu.value = view.id
        StickerImageViewImpl(activity).apply {
            activity.selectSticker(this)
            showSelectItemDialog()
        }.also {
            frame.addView(it)
        }
    }

    fun onVideo(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }

    fun onSwitch(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }

    fun onRadio(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }

    fun onCheck(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }

    fun onSlider(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }
}
