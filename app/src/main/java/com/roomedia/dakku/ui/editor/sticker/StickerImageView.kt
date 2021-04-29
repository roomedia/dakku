package com.roomedia.dakku.ui.editor.sticker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatImageView
import coil.api.load
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.MimeTypes
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.StickerListActivity
import com.roomedia.dakku.ui.editor.menu.MenuBarManager
import java.util.Date

interface StickerImageView : StickerView {

    val type: MimeTypes
    var uri: Uri?
    val requestActivity: ActivityResultLauncher<Intent>

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
            it.uri = uri
        }
    }

    fun showSelectItemDialog() {
        val intent = Intent(getContext(), StickerListActivity::class.java)
            .putExtra("mime_types", type.mimeType)
        requestActivity.launch(intent)
    }

    override fun hidePrivacyContent() {}
    override fun showPrivacyContent() {}
}

@SuppressLint("ViewConstructor")
class StickerImageViewImpl(menuHandlersManager: MenuBarManager) :
    AppCompatImageView(menuHandlersManager.context, null, 0), StickerImageView {

    override var ratio: Float? = null
    override lateinit var pastTouchPos: Pair<Float, Float>
    override var pastTouchAngle: Float? = null
    override var pastTouchSpan: Float? = null

    override val type = MimeTypes.Image
    override var uri: Uri? = null
    override val requestActivity = menuHandlersManager.requestActivity

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_ImageView)
        setOnClickListener {
            if (isSelected) {
                showSelectItemDialog()
            } else {
                menuHandlersManager.selectSticker(this)
            }
        }
    }

    constructor(menuHandlersManager: MenuBarManager, sticker: Sticker) :
        this(menuHandlersManager) {
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super<StickerImageView>.onTouchEvent(event)
    }
}
