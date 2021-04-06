package com.roomedia.dakku.ui.editor.sticker

import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.persistence.StickerType

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
