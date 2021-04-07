package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.SelectLifecycleObserver
import java.util.Date

interface StickerImageView : StickerView {

    val type: String
    val observer: SelectLifecycleObserver

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        // TODO: 2021/04/06 not yet implemented
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            // TODO: 2021/04/06 not yet implemented
        }
    }

    fun showSelectItemDialog() {
        observer.selectItem(type)
    }

    override fun hidePrivacyContent() {}
    override fun showPrivacyContent() {}
}

@SuppressLint("ViewConstructor")
class StickerImageViewImpl(activity: DiaryEditorActivity) :
    AppCompatImageView(activity, null, 0), StickerImageView {

    override lateinit var baseTranslation: Pair<Float, Float>
    override var baseRotation: Float? = null
    override lateinit var baseScale: Pair<Float, Float>
    override var baseRatio: Float? = null

    override lateinit var baseTouchPoint: Pair<Float, Float>
    override var baseTouchAngle: Float? = null
    override var baseTouchSpan: Float? = null

    override val type = "image/*"
    override val observer = activity.observer

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_ImageView)
        setOnClickListener {
            if (isSelected) {
                showSelectItemDialog()
            } else {
                activity.select(this)
            }
        }
    }

    constructor(activity: DiaryEditorActivity, sticker: Sticker) : this(activity) {
        fromSticker(sticker)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.type = StickerType.IMAGE_VIEW
        }
    }
}
