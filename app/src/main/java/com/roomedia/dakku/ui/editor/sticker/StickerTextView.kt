package com.roomedia.dakku.ui.editor.sticker

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date

interface StickerTextView : StickerView {

    fun getContext(): Context
    fun getText(): CharSequence
    fun setText(text: CharSequence?)
    fun setTextColor(color: Int)

    var spannableString: SpannableString?
    var backgroundColorSpan: BackgroundColorSpan?

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        // TODO: 2021/04/05 change background color to a value from database
        setSpannedText(sticker.text, Color.BLACK, Color.TRANSPARENT)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.text = getText()
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

class StickerTextViewImpl(context: Context) :
    AppCompatTextView(context, null, 0), StickerTextView {

    override var spannableString: SpannableString? = null
    override var backgroundColorSpan: BackgroundColorSpan? = null

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_TextView)
        setOnClickListener { showEditTextDialog() }
    }

    constructor(context: Context, sticker: Sticker) : this(context) {
        fromSticker(sticker)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.type = StickerType.TEXT_VIEW
        }
    }
}
