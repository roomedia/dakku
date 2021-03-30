package com.roomedia.dakku.ui.editor

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector

data class Delta(val x: Float, val y: Float)

fun MotionEvent.getDelta(): Delta? {
    if (pointerCount == 1) return null
    return Delta(getX(1) - getX(0), getY(1) - getY(0))
}

class TransformGestureDetector(context: Context) : ScaleGestureDetector(context, SimpleOnScaleGestureListener()) {

    private var inputBoxComponent: InputBoxComponent? = null

    fun setInputBoxComponent(inputBoxComponent: InputBoxComponent) {
        this.inputBoxComponent = inputBoxComponent
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
        inputBoxComponent?.initTranslation(event.x, event.y)
    }

    private fun onTouchMoveEvent(event: MotionEvent) {
        if (event.pointerCount == 1) {
            inputBoxComponent?.translation(event.x, event.y)
            return
        }
        event.getDelta()?.let { delta ->
            inputBoxComponent?.rotation(delta)
            inputBoxComponent?.scale(delta, true)
        }
    }

    private fun onTouchUpEvent() {
        inputBoxComponent?.deinit()
    }
}
