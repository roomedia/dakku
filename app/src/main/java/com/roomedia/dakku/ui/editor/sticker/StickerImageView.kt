package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import coil.api.load
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.DakkuApplication
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.SelectLifecycleObserver
import java.io.File
import java.util.Date

interface StickerImageView : StickerView {

    val type: String
    val observer: SelectLifecycleObserver
    var uri: Uri?

    fun loadImage(uri: Uri?)
    fun setImage(uri: Uri?) {
        this.uri = uri
        loadImage(uri)
        val layoutParams = getLayoutParams().apply {
            width = WRAP_CONTENT
            height = WRAP_CONTENT
        }
        setLayoutParams(layoutParams)
    }

    override fun onTouchUp() {
        loadImage(uri)
        super.onTouchUp()
    }

    override fun fromSticker(sticker: Sticker) {
        super.fromSticker(sticker)
        setImage(sticker.uri)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex)?.also {
            cloneContent(uri)
            it.uri = uri
        }
    }

    private fun cloneContent(uri: Uri?) {
        val inputStream = getContext().contentResolver.openInputStream(uri!!)!!
        // TODO: change extension as extension of source content
        val outputFile = File(DakkuApplication.instance.mediaFolder, "${Date().time}.jpg")
        val outputStream = outputFile.outputStream()

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        this.uri = outputFile.toUri()
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

    override var ratio: Float? = null
    override lateinit var pastTouchPos: Pair<Float, Float>
    override var pastTouchAngle: Float? = null
    override var pastTouchSpan: Float? = null

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
                activity.selectSticker(this)
            }
        }
    }

    constructor(activity: DiaryEditorActivity, sticker: Sticker) : this(activity) {
        fromSticker(sticker)
    }

    override fun loadImage(uri: Uri?) {
        load(uri) {
            allowHardware(false)
        }
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return super.toSticker(diaryId, zIndex)?.also {
            it.type = StickerType.IMAGE_VIEW
        }
    }
}
