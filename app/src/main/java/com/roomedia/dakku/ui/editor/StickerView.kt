package com.roomedia.dakku.ui.editor

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.util.showEditTextDialog

interface StickerView {

    fun getId(): Int
    fun getTranslationX(): Float
    fun getTranslationY(): Float
    fun getRotation(): Float
    fun getScaleX(): Float
    fun getScaleY(): Float

    fun setId(id: Int)
    fun setTranslationX(x: Float)
    fun setTranslationY(y: Float)
    fun setRotation(rot: Float)
    fun setScaleX(w: Float)
    fun setScaleY(h: Float)

    fun fromSticker(sticker: Sticker) {
        with(sticker) {
            setId(id)
            setTranslationX(x)
            setTranslationY(y)
            setRotation(rot)
            setScaleX(w)
            setScaleY(h)
        }
    }

    fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return Sticker(
            diaryId,
            getTranslationX(),
            getTranslationY(),
            getScaleX(),
            getScaleY(),
            getRotation(),
            zIndex,
            StickerType.TEXT_VIEW,
            id = if (getId() != -1) getId() else 0
        )
    }
}

interface StickerTextView : StickerView {

    fun getContext(): Context
    fun getText(): CharSequence
    fun setText(text: CharSequence?)
    fun setOnClickListener(l: View.OnClickListener?)

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setText(sticker.text)
        setOnClickListener { showEditTextDialog() }
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.text = getText()
        }
    }

    fun showEditTextDialog() {
        showEditTextDialog(getContext(), getText()) {
            setText(it)
        }
    }
}

class StickerTextViewImpl(context: Context) :
    AppCompatTextView(context, null, 0), StickerTextView {

    init {
        style(R.style.Sticker_TextView)
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
