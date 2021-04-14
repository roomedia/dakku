package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.backgroundTint
import com.airbnb.paris.extensions.gravity
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.textColor
import com.airbnb.paris.extensions.textStyle
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date

interface StickerTextView : StickerView {

    fun getText(): CharSequence
    fun setText(text: CharSequence?)

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setText(sticker.text)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        val text = getText()
        return when {
            text.isNotBlank() -> super.toSticker(diaryId, zIndex)!!.also {
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

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_TextView)
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

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        style {
            // TODO: 2021/04/05 change background color to a value from database
            textColor(Color.BLACK)
            backgroundTint(null)
            textStyle(sticker.textStyle)
            gravity(sticker.textAlignment ?: Gravity.START)
        }
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex)?.also {
            it.type = StickerType.TEXT_VIEW
            it.textStyle = typeface.style
            it.textAlignment = gravity
        }
    }

    override fun hidePrivacyContent() {
        this.backgroundTintList?.defaultColor
        style {
            textColor(Color.TRANSPARENT)
            if (backgroundTintList == null) {
                backgroundTint(Color.LTGRAY)
            }
            textStyle(typeface.style)
        }
    }

    override fun showPrivacyContent() {
        // TODO: 2021/04/05 change background color to a value from database
        style {
            textColor(Color.BLACK)
            backgroundTint(null)
            textStyle(typeface.style)
        }
    }
}
