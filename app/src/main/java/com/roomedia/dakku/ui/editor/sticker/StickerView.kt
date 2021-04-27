package com.roomedia.dakku.ui.editor.sticker

import android.content.Context
import android.view.ViewGroup
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.ui.editor.Delta
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

interface StickerView {

    var ratio: Float?
    var pastTouchPos: Pair<Float, Float>
    var pastTouchAngle: Float?
    var pastTouchSpan: Float?

    fun getId(): Int
    fun getContext(): Context
    fun getTranslationX(): Float
    fun getTranslationY(): Float
    fun getRotation(): Float
    fun getWidth(): Int
    fun getHeight(): Int
    fun getLayoutParams(): ViewGroup.LayoutParams

    fun setId(id: Int)
    fun setTranslationX(x: Float)
    fun setTranslationY(y: Float)
    fun setRotation(rot: Float)
    fun setLayoutParams(layoutParams: ViewGroup.LayoutParams)
    fun setSelected(isSelected: Boolean)

    fun initTranslation(x: Float, y: Float) {
        pastTouchPos = Pair(x, y)
    }

    fun setTranslation(x: Float, y: Float) {
        val dx = x - pastTouchPos.first
        val dy = y - pastTouchPos.second
        setTranslationX(getTranslationX() + dx)
        setTranslationY(getTranslationY() + dy)
        pastTouchPos = Pair(x, y)
    }

    fun setRotation(delta: Delta) {
        val angle = atan2(delta.y, delta.x)
        if (pastTouchAngle == null) {
            pastTouchAngle = angle
            return
        }
        val deltaAngle = toDegrees(angle - pastTouchAngle!!)
        setRotation(getRotation() + deltaAngle)
        pastTouchAngle = angle
    }

    fun setScale(delta: Delta) {
        val span = sqrt(delta.x.pow(2) + delta.y.pow(2))
        if (pastTouchSpan == null) {
            ratio = getHeight().toFloat() / getWidth().toFloat()
            pastTouchSpan = span
            return
        }
        val deltaSpan = (span - pastTouchSpan!!).toInt()
        val layoutParams = getLayoutParams().apply {
            width = getWidth() + deltaSpan
            height = (ratio!! * width).toInt()
        }
        setLayoutParams(layoutParams)
        pastTouchSpan = span
    }

    fun onTouchUp() {
        pastTouchAngle = null
        pastTouchSpan = null
    }

    private fun toDegrees(value: Float): Float {
        return (value / PI.toFloat() * 180f) % 360f
    }

    fun fromSticker(sticker: Sticker) {
        with(sticker) {
            setId(id)
            setTranslationX(x)
            setTranslationY(y)
            setRotation(rot)
            getLayoutParams().width = w
            getLayoutParams().height = h
        }
    }

    fun toSticker(diaryId: Long, zIndex: Int): Sticker? {
        return Sticker(
            getId(),
            diaryId,
            getTranslationX(),
            getTranslationY(),
            getWidth(),
            getHeight(),
            getRotation(),
            zIndex,
        )
    }

    fun hidePrivacyContent()
    fun showPrivacyContent()
}
