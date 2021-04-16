package com.roomedia.dakku.ui.editor.menu

import android.widget.SeekBar
import androidx.databinding.ObservableFloat
import com.roomedia.dakku.normalize
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

enum class SliderType {
    SCALE,
    LINE_SPACING,
    LETTER_SPACING,
}

const val SCALE_START = 45
const val SCALE_END = 100

const val LINE_SPACING_START = 1
const val LINE_SPACING_END = 3

const val LETTER_SPACING_START = 0
const val LETTER_SPACING_END = 2.9F

const val SLIDER_START = 0
const val SLIDER_END = 100

fun Number.toSlider(type: SliderType): Int {
    val (srcStart, srcEnd) = when (type) {
        SliderType.SCALE -> Pair(SCALE_START, SCALE_END)
        SliderType.LINE_SPACING -> Pair(LINE_SPACING_START, LINE_SPACING_END)
        SliderType.LETTER_SPACING -> Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, srcStart, srcEnd, SLIDER_START, SLIDER_END).toInt()
}

fun Number.fromSlider(type: SliderType): Float {
    val (dstStart, dstEnd) = when (type) {
        SliderType.SCALE -> Pair(SCALE_START, SCALE_END)
        SliderType.LINE_SPACING -> Pair(LINE_SPACING_START, LINE_SPACING_END)
        SliderType.LETTER_SPACING -> Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, SLIDER_START, SLIDER_END, dstStart, dstEnd)
}

interface OnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
    var stickerView: StickerTextViewImpl
    fun setSticker(stickerView: StickerTextViewImpl)
}

class ScaleListener(
    private val seekBar: SeekBar,
    stickerView: StickerTextViewImpl,
    private val observableTextSize: ObservableFloat,
) : OnSeekBarChangeListener {

    override lateinit var stickerView: StickerTextViewImpl

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        this.stickerView = stickerView
        seekBar.progress = stickerView.textSize.toSlider(SliderType.SCALE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val newTextSize = progress.fromSlider(SliderType.SCALE).toInt()
        with(stickerView) {
            setStyle(
                textSize = newTextSize,
                lineSpacing = (lineHeight.toFloat() / textSize * newTextSize).toInt(),
            )
        }
        observableTextSize.set(stickerView.textSize)
    }
}

class LineSpacingListener(
    private val seekBar: SeekBar,
    stickerView: StickerTextViewImpl
) : OnSeekBarChangeListener {

    override lateinit var stickerView: StickerTextViewImpl

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        this.stickerView = stickerView
        seekBar.progress = stickerView.lineHeight.toSlider(SliderType.LINE_SPACING)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        with(stickerView) {
            val newLineSpacing = progress.fromSlider(SliderType.LINE_SPACING).toInt()
            setStyle(
                lineSpacing = (textSize * newLineSpacing).toInt(),
            )
        }
    }
}

class LetterSpacingListener(
    private val seekBar: SeekBar,
    stickerView: StickerTextViewImpl
) : OnSeekBarChangeListener {

    override lateinit var stickerView: StickerTextViewImpl

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        this.stickerView = stickerView
        seekBar.progress = stickerView.letterSpacing.toSlider(SliderType.LETTER_SPACING)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerView.setStyle(
            letterSpacing = progress.fromSlider(SliderType.LETTER_SPACING),
        )
    }
}
