package com.roomedia.dakku.ui.editor.menu

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.SeekBar
import com.airbnb.paris.extensions.layoutHeight
import com.airbnb.paris.extensions.layoutWidth
import com.airbnb.paris.extensions.letterSpacing
import com.airbnb.paris.extensions.lineHeight
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.LETTER
import com.roomedia.dakku.LINE
import com.roomedia.dakku.sliderToSpacing
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class LineSpacingListener(private val sticker: StickerTextViewImpl) :
    SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        sticker.style {
            layoutWidth(sticker.width)
            layoutHeight(WRAP_CONTENT)
            textStyle(sticker.typeface.style)
            lineHeight(progress.sliderToSpacing(LINE).toInt())
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
            textStyle(sticker.typeface.style)
            letterSpacing(progress.sliderToSpacing(LETTER))
        }
    }
}
