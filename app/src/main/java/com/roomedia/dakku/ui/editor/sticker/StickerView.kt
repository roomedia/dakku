package com.roomedia.dakku.ui.editor.sticker

import android.content.Context
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.Delta
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

interface StickerView {

    var baseTranslation: Pair<Float, Float>
    var baseRotation: Float?
    var baseScale: Pair<Float, Float>
    var baseRatio: Float?

    var baseTouchPoint: Pair<Float, Float>
    var baseTouchAngle: Float?
    var baseTouchSpan: Float?

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
