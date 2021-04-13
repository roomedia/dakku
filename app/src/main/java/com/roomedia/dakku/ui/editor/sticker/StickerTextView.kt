package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date

interface StickerTextView : StickerView {

    fun getGravity(): Int
    fun getText(): CharSequence

    fun setGravity(gravity: Int)
    fun setText(text: CharSequence?)
    fun setTextColor(color: Int)

    var spannableString: SpannableString?
    var backgroundColorSpan: BackgroundColorSpan?

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        // TODO: 2021/04/05 change background color to a value from database
        setSpannedText(sticker.text, Color.BLACK, Color.TRANSPARENT)
        setGravity(sticker.textAlignment ?: Gravity.START)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        val text = getText()
        return when {
            text.isNotBlank() -> super.toSticker(diaryId, zIndex)!!.also {
                it.text = text
                it.textAlignment = getGravity()
            }
            else -> null
        }
    }

    private fun setSpannedText(text: CharSequence?, textColor: Int, backgroundColor: Int) {
        backgroundColorSpan?.let {
            spannableString!!.removeSpan(it)
        }
        backgroundColorSpan = BackgroundColorSpan(backgroundColor)
        spannableString = SpannableString(text).apply {
            setSpan(backgroundColorSpan, 0, count(), SpannableString.SPAN_MARK_MARK)
        }
        setTextColor(textColor)
        setText(spannableString)
    }

    fun showEditTextDialog() {
        showEditTextDialog(getContext(), getText()) {
            // TODO: 2021/04/05 change background color to a value from database
            setSpannedText(it, Color.BLACK, Color.TRANSPARENT)
        }
    }

    override fun hidePrivacyContent() {
        // TODO: 2021/04/05 change background color to a value from database
        setSpannedText(getText(), Color.GRAY, Color.GRAY)
    }

    override fun showPrivacyContent() {
        // TODO: 2021/04/05 change background color to a value from database
        setSpannedText(getText(), Color.BLACK, Color.TRANSPARENT)
    }
}

@SuppressLint("ViewConstructor")
class StickerTextViewImpl(activity: DiaryEditorActivity) :
    AppCompatTextView(activity, null, 0), StickerTextView {

    override var ratio: Float? = null
    override lateinit var pastTouchPos: Pair<Float, Float>
    override var pastTouchAngle: Float? = null
    override var pastTouchSpan: Float? = null

    override var spannableString: SpannableString? = null
    override var backgroundColorSpan: BackgroundColorSpan? = null

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

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex).also {
            it?.type = StickerType.TEXT_VIEW
        }
    }
}
