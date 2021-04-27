package com.roomedia.dakku.ui.editor

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.ui.editor.sticker.StickerView

data class Delta(val x: Float, val y: Float)

fun MotionEvent.getDelta(): Delta? {
    if (pointerCount == 1) return null
    return Delta(getX(1) - getX(0), getY(1) - getY(0))
}

class TransformGestureDetector(context: Context) :
    ScaleGestureDetector(context, SimpleOnScaleGestureListener()) {

    private var count = 0
    private val threshold = 10
    val selectedSticker = MutableLiveData<StickerView?>(null)

    fun onTouchEvent(stickerView: StickerView?, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.pointerCount == 1) count = 0
                onTouchDownEvent(event)
            }
            MotionEvent.ACTION_MOVE -> {
                count++
                onTouchMoveEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                if (count < threshold) {
                    (stickerView as? View)?.performClick()
                    selectedSticker.value = stickerView
                }
            }
            else -> return false
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                onTouchDownEvent(event)
            }
            MotionEvent.ACTION_MOVE -> {
                onTouchMoveEvent(event)
            }
            MotionEvent.ACTION_UP -> {
                onTouchUpEvent()
            }
            else -> return false
        }
        return true
    }

    private fun onTouchDownEvent(event: MotionEvent) {
        selectedSticker.value?.initTranslation(event.x, event.y)
    }

    private fun onTouchMoveEvent(event: MotionEvent) {
        if (event.pointerCount == 1) {
            selectedSticker.value?.setTranslation(event.x, event.y)
            return
        }
        event.getDelta()?.let { delta ->
            selectedSticker.value?.setRotation(delta)
            selectedSticker.value?.setScale(delta)
        }
    }

    private fun onTouchUpEvent() {
        selectedSticker.value?.onTouchUp()
    }

    companion object {
        var instance: TransformGestureDetector? = null
        fun getInstance(context: Context): TransformGestureDetector {
            if (instance == null) {
                instance = TransformGestureDetector(context)
            }
            return instance!!
        }
    }
}
