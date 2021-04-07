package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.net.Uri
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
    var uri: Uri?

    fun setImageURI(uri: Uri?)

    fun setImage(uri: Uri?) {
        this.uri = uri
        setImageURI(uri)
    }

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setImage(sticker.uri)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return when (uri) {
            null -> null
            else -> super.toSticker(diaryId, zIndex).also {
                it?.uri = uri
            }
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
    override var uri: Uri? = null

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

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex).also {
            it?.type = StickerType.IMAGE_VIEW
        }
    }
}
