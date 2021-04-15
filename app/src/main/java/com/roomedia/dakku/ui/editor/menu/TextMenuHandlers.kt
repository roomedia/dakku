package com.roomedia.dakku.ui.editor.menu

import android.view.Gravity
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.R
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class TextMenuHandlers(
    lifecycleOwner: LifecycleOwner,
    private val selectedSticker: MutableLiveData<StickerView?>,
    val textColor: ObservableInt,
) {

    val isBold = ObservableBoolean()
    val isItalic = ObservableBoolean()

    private var alignIndex = 0
    val alignIcon = ObservableInt()

    init {
        selectedSticker.observe(lifecycleOwner) { sticker ->
            (sticker as? StickerTextViewImpl)?.apply {
                isBold.set(typeface.isBold)
                isItalic.set(typeface.isItalic)
                textColor.set(currentTextColor)
                alignIndex = Alignments.indexOf(gravity)
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

    fun onColor() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            // TODO: 2021/04/14 set slider VISIBLE/GONE for all button -> do when working on undo/redo
        }
    }

    fun onAlign() {
        alignIndex = (alignIndex + 1) % Alignments.size
        alignIcon.set(AlignmentIcons[alignIndex])
        val sticker = selectedSticker.value as? StickerTextViewImpl
        sticker?.gravity = Alignments[alignIndex]
    }

    fun onBold() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isBold.set(!typeface.isBold)
            var textStyle = if (typeface.isBold) 0 else 1
            textStyle += if (typeface.isItalic) 2 else 0
            style {
                textStyle(textStyle)
            }
        }
    }

    fun onItalic() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isItalic.set(!typeface.isItalic)
            var textStyle = if (typeface.isBold) 1 else 0
            textStyle += if (typeface.isItalic) 0 else 2
            style {
                textStyle(textStyle)
            }
        }
    }

    fun onSpacing() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            // TODO: 2021/04/14 set slider VISIBLE/GONE for all button -> do when working on undo/redo
        }
    }

    companion object {
        val Alignments = listOf(
            Gravity.START + Gravity.CENTER_VERTICAL,
            Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL,
            Gravity.END + Gravity.CENTER_VERTICAL,
        )
        val AlignmentIcons = listOf(
            R.drawable.ic_align_left,
            R.drawable.ic_align_center,
            R.drawable.ic_align_right,
        )
    }
}
