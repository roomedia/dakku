package com.roomedia.dakku.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.paris.extensions.style
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.util.showEditTextDialog
import java.util.Date
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

interface StickerView {

    fun getId(): Int
    fun getContext(): Context
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
    fun setSelected(isSelected: Boolean)

    var baseTranslation: Pair<Float, Float>
    var baseRotation: Float?
    var baseScale: Pair<Float, Float>
    var baseRatio: Float?

    var baseTouchPoint: Pair<Float, Float>
    var baseTouchAngle: Float?
    var baseTouchSpan: Float?

    fun initTranslation(x: Float, y: Float) {
        baseTranslation = Pair(getTranslationX(), getTranslationY())
        baseTouchPoint = Pair(x, y)
    }

    private fun initRotation(delta: Delta) {
        baseRotation = getRotation()
        baseTouchAngle = atan2(delta.y, delta.x)
    }

    private fun initScale(delta: Delta) {
        baseScale = Pair(getScaleX(), getScaleY())
        baseRatio = baseScale.second / baseScale.first
        baseTouchSpan = sqrt(delta.x.pow(2) + delta.y.pow(2))
    }

    fun setTranslation(x: Float, y: Float) {
        val dx = x - baseTouchPoint.first
        val dy = y - baseTouchPoint.second
        setTranslationX(baseTranslation.first + dx)
        setTranslationY(baseTranslation.second + dy)
    }

    fun setRotation(delta: Delta) {
        if (baseTouchAngle == null) {
            initRotation(delta)
            return
        }
        val angle = atan2(delta.y, delta.x)
        val deltaAngle = toDegrees(angle - baseTouchAngle!!)
        setRotation(baseRotation!! + deltaAngle)
    }

    fun setScale(delta: Delta) {
        if (baseTouchSpan == null) {
            initScale(delta)
            return
        }
        val span = sqrt(delta.x.pow(2) + delta.y.pow(2))
        val deltaSpan = normalize(span - baseTouchSpan!!, getContext().resources.displayMetrics.widthPixels.toFloat(), 5F)
        setScaleX(baseScale.first + deltaSpan)
        setScaleY(baseRatio!! * getScaleX())
    }

    fun destroy() {
        baseRotation = null
        baseRatio = null
        baseTouchAngle = null
        baseTouchSpan = null
    }

    private fun toDegrees(value: Float): Float {
        return (value / PI.toFloat() * 180F) % 360F
    }

    private fun normalize(value: Float, srcLimit: Float, dstLimit: Float): Float {
        return value * dstLimit / srcLimit
    }

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
            getId(),
            diaryId,
            getTranslationX(),
            getTranslationY(),
            getScaleX(),
            getScaleY(),
            getRotation(),
            zIndex,
            StickerType.TEXT_VIEW,
        )
    }

    fun hidePrivacyContent()
    fun showPrivacyContent()
}

interface StickerTextView : StickerView {

    fun getText(): CharSequence
    fun setText(text: CharSequence?)
    fun setTextColor(color: Int)
    fun setOnClickListener(l: View.OnClickListener?)

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

@SuppressLint("ViewConstructor")
class StickerTextViewImpl(activity: DiaryEditorActivity) :
    AppCompatTextView(activity, null, 0), StickerTextView {

    override lateinit var baseTranslation: Pair<Float, Float>
    override var baseRotation: Float? = null
    override lateinit var baseScale: Pair<Float, Float>
    override var baseRatio: Float? = null

    override lateinit var baseTouchPoint: Pair<Float, Float>
    override var baseTouchAngle: Float? = null
    override var baseTouchSpan: Float? = null

    override var spannableString: SpannableString? = null
    override var backgroundColorSpan: BackgroundColorSpan? = null

    init {
        id = Date().time.toInt()
        style(R.style.Sticker_TextView)
        setOnClickListener {
            if (isSelected) {
                showEditTextDialog()
            } else {
                isSelected = true
                activity.selectedSticker?.setSelected(false)
                activity.selectedSticker = this
            }
        }
    }

    constructor(context: DiaryEditorActivity, sticker: Sticker) : this(context) {
        fromSticker(sticker)
    }

    override fun toSticker(diaryId: Long, zIndex: Int): Sticker {
        return super.toSticker(diaryId, zIndex).also {
            it.type = StickerType.TEXT_VIEW
        }
    }
}
