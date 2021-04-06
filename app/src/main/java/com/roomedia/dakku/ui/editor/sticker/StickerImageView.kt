package com.roomedia.dakku.ui.editor.sticker

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import java.util.Date

interface StickerImageView : StickerView {

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        // TODO: 2021/04/06 not yet implemented
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            // TODO: 2021/04/06 not yet implemented
        }
    }

    fun showSelectImageDialog() {
        // TODO: 2021/04/06 not yet implemented
    }

    override fun hidePrivacyContent() {}
    override fun showPrivacyContent() {}
}

class StickerImageViewImpl(context: Context) :
    AppCompatImageView(context, null, 0), StickerImageView {

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_ImageView)
        setOnClickListener { showSelectImageDialog() }
    }

    constructor(context: Context, sticker: Sticker) : this(context) {
        fromSticker(sticker)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.type = StickerType.IMAGE_VIEW
        }
    }
}
