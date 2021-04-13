package com.roomedia.dakku.ui.editor.menu

import android.view.Gravity
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.R
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class TextMenuHandlers(
    lifecycleOwner: LifecycleOwner,
    private val selectedSticker: MutableLiveData<StickerView?>,
) {

    private var alignIndex = 0
    val alignIcon = ObservableInt()

    init {
        selectedSticker.observe(lifecycleOwner) { sticker ->
            (sticker as? StickerTextViewImpl)?.gravity?.also { flag ->
                alignIndex = Alignments.indexOf(flag)
            }
            alignIcon.set(AlignmentIcons[alignIndex])
        }
    }

    fun onFont() {
        TODO("not yet implemented")
    }

    fun onSize() {
        TODO("not yet implemented")
    }

    fun onAlign(view: View) {
        alignIndex = (alignIndex + 1) % Alignments.size
        alignIcon.set(AlignmentIcons[alignIndex])
        val sticker = selectedSticker.value as? StickerTextViewImpl
        sticker?.gravity = Alignments[alignIndex]
    }

    fun onLetterSpacing() {
        TODO("not yet implemented")
    }

    fun onLineSpacing() {
        TODO("not yet implemented")
    }

    fun onBold() {
        TODO("not yet implemented")
    }

    fun onItalic() {
        TODO("not yet implemented")
    }

    fun onStroke() {
        TODO("not yet implemented")
    }

    fun onUnderline() {
        TODO("not yet implemented")
    }

    companion object {
        val Alignments = listOf(
            Gravity.START + Gravity.TOP,
            Gravity.CENTER_HORIZONTAL + Gravity.TOP,
            Gravity.END + Gravity.TOP,
        )
        val AlignmentIcons = listOf(
            R.drawable.ic_align_left,
            R.drawable.ic_align_center,
            R.drawable.ic_align_right,
        )
    }
}
