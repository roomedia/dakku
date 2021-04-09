package com.roomedia.dakku.ui.editor

import android.view.MotionEvent
import android.view.ScaleGestureDetector

data class Delta(val x: Float, val y: Float)

fun MotionEvent.getDelta(): Delta? {
    if (pointerCount == 1) return null
    return Delta(getX(1) - getX(0), getY(1) - getY(0))
}

class TransformGestureDetector(private val activity: DiaryEditorActivity) :
    ScaleGestureDetector(activity, SimpleOnScaleGestureListener()) {

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
        activity.selectedSticker?.initTranslation(event.x, event.y)
    }

    private fun onTouchMoveEvent(event: MotionEvent) {
        if (event.pointerCount == 1) {
            activity.selectedSticker?.setTranslation(event.x, event.y)
            return
        }
        event.getDelta()?.let { delta ->
            activity.selectedSticker?.setRotation(delta)
            activity.selectedSticker?.setScale(delta)
        }
    }

    private fun onTouchUpEvent() {
        activity.selectedSticker?.onTouchUp()
    }
}
