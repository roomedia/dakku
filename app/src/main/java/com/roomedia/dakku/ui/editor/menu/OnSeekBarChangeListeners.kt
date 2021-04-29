package com.roomedia.dakku.ui.editor.menu

import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.databinding.ObservableFloat
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

interface OnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    val seekBar: SeekBar
    val start get() = 0
    val end get() = 0
    val span get() = end - start

    var stickerView: StickerView
    val stickerViewImpl get() = stickerView as View
    val stickerTextViewImpl get() = stickerView as? StickerTextViewImpl

    fun setStickerCallback()

    fun setSticker(stickerView: StickerView) {
        this.stickerView = stickerView
        seekBar.setOnSeekBarChangeListener(null)
        setStickerCallback()
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}

class ScaleListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerView,
    private val observableTextSize: ObservableFloat,
) : OnSeekBarChangeListener {

    override val start = 50
    override val end = 100

    init {
        setSticker(stickerView)
    }

    override fun setStickerCallback() {
        seekBar.max = span
        stickerTextViewImpl?.let {
            seekBar.progress = it.textSize.toInt() - start
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerTextViewImpl?.apply {
            setStyle(textSize = start + progress)
            observableTextSize.set(textSize)
        }
    }
}

class LineSpacingListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerView,
) : OnSeekBarChangeListener {

    override val start = 10
    override val end = 30

    init {
        setSticker(stickerView)
    }

    override fun setStickerCallback() {
        seekBar.max = span
        stickerTextViewImpl?.let {
            seekBar.progress = (it.lineSpacingMultiplier * 10).toInt() - start
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerTextViewImpl?.apply {
            setStyle(lineSpacing = (start + progress) / 10f)
        }
    }
}

class LetterSpacingListener(
    override val seekBar: SeekBar,
    override var stickerView: StickerView,
) : OnSeekBarChangeListener {

    override val end = 30

    init {
        setSticker(stickerView)
    }

    override fun setStickerCallback() {
        seekBar.max = span
        stickerTextViewImpl?.let {
            seekBar.progress = (it.letterSpacing * 10).toInt() - start
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        stickerTextViewImpl?.apply {
            setStyle(letterSpacing = (start + progress) / 10f)
        }
    }
}

class LayerListener(
    private val frame: FrameLayout,
    override val seekBar: SeekBar,
    override var stickerView: StickerView,
) : OnSeekBarChangeListener {

    init {
        setSticker(stickerView)
    }

    override fun setStickerCallback() {
        seekBar.max = frame.childCount - 1
        seekBar.progress = frame.indexOfChild(stickerView as View)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        frame.removeView(stickerViewImpl)
        frame.addView(stickerViewImpl, progress)
    }
}
