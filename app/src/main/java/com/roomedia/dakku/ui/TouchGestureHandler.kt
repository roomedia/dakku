package com.roomedia.dakku.ui

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector

class TouchGestureHandler(context : Context) : ScaleGestureDetector(context, SimpleOnScaleGestureListener()) {

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
        inputBoxComponent?.translation(event.x, event.y)
    }

    private fun onTouchUpEvent() {}
}

