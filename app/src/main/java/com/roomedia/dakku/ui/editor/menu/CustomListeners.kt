package com.roomedia.dakku.ui.editor.menu

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import com.airbnb.paris.extensions.layoutHeight
import com.airbnb.paris.extensions.layoutWidth
import com.airbnb.paris.extensions.letterSpacing
import com.airbnb.paris.extensions.lineHeight
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textColor
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.normalize
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

enum class SliderType {
    LINE_SPACING,
    LETTER_SPACING,
}

const val LINE_SPACING_START = 50
const val LINE_SPACING_END = 150

const val LETTER_SPACING_START = 0F
const val LETTER_SPACING_END = 1F

const val SLIDER_START = 0F
const val SLIDER_END = 100F

fun Number.toSlider(type: SliderType): Int {
    val (srcStart, srcEnd) = when (type) {
        SliderType.LINE_SPACING -> Pair(LINE_SPACING_START, LINE_SPACING_END)
        SliderType.LETTER_SPACING -> Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, srcStart, srcEnd, SLIDER_START, SLIDER_END).toInt()
}

fun Number.fromSlider(type: SliderType): Float {
    val (dstStart, dstEnd) = when (type) {
        SliderType.LINE_SPACING -> Pair(LINE_SPACING_START, LINE_SPACING_END)
        SliderType.LETTER_SPACING -> Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, SLIDER_START, SLIDER_END, dstStart, dstEnd)
}

class LineSpacingListener(private val sticker: StickerTextViewImpl) :
    SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        sticker.style {
            layoutWidth(sticker.width)
            layoutHeight(WRAP_CONTENT)
            textColor(sticker.currentTextColor)
            textStyle(sticker.typeface.style)
            lineHeight(progress.fromSlider(SliderType.LINE_SPACING).toInt())
        }
    }
}

class LetterSpacingListener(private val sticker: StickerTextViewImpl) :
    SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        sticker.style {
            layoutWidth(WRAP_CONTENT)
            layoutHeight(sticker.height)
            textColor(sticker.currentTextColor)
            textStyle(sticker.typeface.style)
            letterSpacing(progress.fromSlider(SliderType.LETTER_SPACING))
        }
    }
}
