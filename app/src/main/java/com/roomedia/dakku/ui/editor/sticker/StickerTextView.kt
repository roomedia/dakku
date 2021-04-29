package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.backgroundTint
import com.airbnb.paris.extensions.fontFamily
import com.airbnb.paris.extensions.fontFamilyRes
import com.airbnb.paris.extensions.gravity
import com.airbnb.paris.extensions.layoutHeight
import com.airbnb.paris.extensions.layoutWidth
import com.airbnb.paris.extensions.letterSpacing
import com.airbnb.paris.extensions.lineSpacingMultiplier
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textColor
import com.airbnb.paris.extensions.textSize
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.Delta
import com.roomedia.dakku.ui.editor.menu.MenuHandlersManager
import com.roomedia.dakku.ui.editor.sticker.StickerTextView.Companion.Fonts
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date

interface StickerTextView : StickerView {

    var fontIndex: Int

    fun getText(): CharSequence
    fun setText(text: CharSequence?)
    override fun setScale(delta: Delta) {}

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setText(sticker.text)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        val text = getText()
        return when {
            text.isNotBlank() -> super.toSticker(diaryId, zIndex)!!.also {
                it.w = WRAP_CONTENT
                it.h = WRAP_CONTENT
                it.text = text
            }
            else -> null
        }
    }

    fun showEditTextDialog() {
        showEditTextDialog(getContext(), getText()) {
            setText(it)
        }
    }

    companion object {
        val Fonts = listOf(
            R.font.null_,
            R.font.nanum_0,
            R.font.nanum_1,
            R.font.nanum_2,
            R.font.nanum_3,
            R.font.nanum_4,
            R.font.cafe24_0,
            R.font.cafe24_1,
            R.font.cafe24_2,
            R.font.cafe24_3,
            R.font.cafe24_4,
            R.font.neodgm,
            R.font.ridibatang,
        )
    }
}

@SuppressLint("ViewConstructor")
class StickerTextViewImpl(menuHandlersManager: MenuHandlersManager) :
    AppCompatTextView(menuHandlersManager.context, null, 0), StickerTextView {

    override var ratio: Float? = null
    override lateinit var pastTouchPos: Pair<Float, Float>
    override var pastTouchAngle: Float? = null
    override var pastTouchSpan: Float? = null

    override var fontIndex: Int = 0
    @ColorInt private var pastTextColor: Int = 0xFF000000.toInt()

    init {
        id = Date().time.toInt()
        style {
            add(R.style.Sticker_TextView)
            textSize(50)
            textColor(0xFF000000.toInt())
        }
        setOnClickListener {
            if (isSelected) {
                showEditTextDialog()
            } else {
                menuHandlersManager.selectSticker(this)
            }
        }
    }

    constructor(menuHandlersManager: MenuHandlersManager, sticker: Sticker) :
        this(menuHandlersManager) {
            fromSticker(sticker)
        }

    fun setStyle(
        fontIndex: Int = this.fontIndex,
        textSize: Int = getTextSize().toInt(),
        textColor: Int = currentTextColor,
        backgroundTint: Int? = null,
        textStyle: Int = typeface.style,
        textAlignment: Int = gravity,
        lineSpacing: Float = lineSpacingMultiplier,
        letterSpacing: Float = getLetterSpacing(),
    ) {
        this.fontIndex = fontIndex
        style {
            layoutWidth(WRAP_CONTENT)
            layoutHeight(WRAP_CONTENT)
            when (fontIndex) {
                0 -> fontFamily(Typeface.DEFAULT)
                else -> fontFamilyRes(Fonts[fontIndex])
            }
            textSize(textSize)
            if (backgroundTint == null) {
                backgroundTint(null)
            } else {
                backgroundTint(backgroundTint)
            }
            textColor(textColor)
            textStyle(textStyle)
            gravity(textAlignment)
            lineSpacingMultiplier(lineSpacing)
            letterSpacing(letterSpacing)
        }
    }

    override fun showEditTextDialog() {
        showEditTextDialog(context, text) {
            text = it
        }
        setStyle()
    }

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setStyle(
            fontIndex = sticker.fontIndex,
            textSize = sticker.textSize,
            textColor = sticker.textColor ?: 0xFF000000.toInt(),
            textStyle = sticker.textStyle,
            textAlignment = sticker.textAlignment ?: Gravity.START,
            lineSpacing = sticker.lineSpacing,
            letterSpacing = sticker.letterSpacing,
        )
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex)?.also {
            it.type = StickerType.TEXT_VIEW
            it.fontIndex = fontIndex
            it.textSize = textSize.toInt()
            it.textColor = currentTextColor
            it.textAlignment = gravity
            it.textStyle = typeface.style
            it.lineSpacing = lineSpacingMultiplier
            it.letterSpacing = letterSpacing
        }
    }

    override fun hidePrivacyContent() {
        pastTextColor = currentTextColor
        setStyle(
            textColor = Color.TRANSPARENT,
            backgroundTint = Color.LTGRAY,
        )
    }

    override fun showPrivacyContent() {
        setStyle(
            textColor = pastTextColor,
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super<StickerTextView>.onTouchEvent(event)
    }
}
