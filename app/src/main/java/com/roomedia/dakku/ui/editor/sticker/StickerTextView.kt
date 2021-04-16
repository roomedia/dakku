package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.backgroundTint
import com.airbnb.paris.extensions.gravity
import com.airbnb.paris.extensions.layoutHeight
import com.airbnb.paris.extensions.layoutWidth
import com.airbnb.paris.extensions.letterSpacing
import com.airbnb.paris.extensions.lineHeight
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textColor
import com.airbnb.paris.extensions.textSize
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.Delta
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date

interface StickerTextView : StickerView {

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
}

@SuppressLint("ViewConstructor")
class StickerTextViewImpl(activity: DiaryEditorActivity) :
    AppCompatTextView(activity, null, 0), StickerTextView {

    override var ratio: Float? = null
    override lateinit var pastTouchPos: Pair<Float, Float>
    override var pastTouchAngle: Float? = null
    override var pastTouchSpan: Float? = null
    private var pastTextColor: Int = 0xFF000000.toInt()

    init {
        id = Date().time.toInt()
        style {
            add(R.style.Sticker_TextView)
            textSize(45)
            textColor(0xFF000000.toInt())
        }
        setOnClickListener {
            if (isSelected) {
                showEditTextDialog()
            } else {
                activity.selectSticker(this)
            }
        }
    }

    constructor(context: DiaryEditorActivity, sticker: Sticker) : this(context) {
        fromSticker(sticker)
    }

    fun setStyle(
        textSize: Int = getTextSize().toInt(),
        textColor: Int = currentTextColor,
        backgroundTint: Int? = null,
        textStyle: Int = typeface.style,
        textAlignment: Int = gravity,
        lineSpacing: Int = lineHeight,
        letterSpacing: Float = getLetterSpacing(),
    ) {
        style {
            layoutWidth(WRAP_CONTENT)
            layoutHeight(WRAP_CONTENT)
            textSize(textSize)
            if (backgroundTint == null) {
                backgroundTint(null)
            } else {
                backgroundTint(backgroundTint)
            }
            textColor(textColor)
            textStyle(textStyle)
            gravity(textAlignment)
            lineHeight(lineSpacing)
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
            it.textSize = textSize.toInt()
            it.textColor = currentTextColor
            it.textAlignment = gravity
            it.textStyle = typeface.style
            it.lineSpacing = lineHeight
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
}
