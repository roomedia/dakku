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

const val SCALE_START = 50
const val SCALE_END = 100

const val LINE_SPACING_START = 1
const val LINE_SPACING_END = 3

const val LETTER_SPACING_START = 0
const val LETTER_SPACING_END = 2.9f

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
    val seekBar: SeekBar
    var stickerView: StickerTextViewImpl

    fun setSticker(stickerView: StickerTextViewImpl) {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}

class ScaleListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerTextViewImpl,
    private val observableTextSize: ObservableFloat,
) : OnSeekBarChangeListener {

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        super.setSticker(stickerView)
        seekBar.progress = stickerView.textSize.toSlider(SliderType.SCALE)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerView.setStyle(
            textSize = progress.fromSlider(SliderType.SCALE).toInt(),
        )
        observableTextSize.set(stickerView.textSize)
    }
}

class LineSpacingListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerTextViewImpl,
) : OnSeekBarChangeListener {

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        super.setSticker(stickerView)
        seekBar.progress = stickerView.lineSpacingMultiplier.toSlider(SliderType.LINE_SPACING)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerView.setStyle(
            lineSpacing = progress.fromSlider(SliderType.LINE_SPACING),
        )
    }
}

class LetterSpacingListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerTextViewImpl,
) : OnSeekBarChangeListener {

    init {
        setSticker(stickerView)
    }

    override fun setSticker(stickerView: StickerTextViewImpl) {
        super.setSticker(stickerView)
        seekBar.progress = stickerView.letterSpacing.toSlider(SliderType.LETTER_SPACING)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerView.setStyle(
            letterSpacing = progress.fromSlider(SliderType.LETTER_SPACING),
        )
    }
}
