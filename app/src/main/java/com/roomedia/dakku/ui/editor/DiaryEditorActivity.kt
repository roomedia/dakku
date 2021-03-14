package com.roomedia.dakku.ui.editor

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.handler.TouchGestureHandler

class DiaryEditorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryEditorBinding.inflate(layoutInflater) }
    private val touchEventHandler by lazy { TouchGestureHandler(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        touchEventHandler.setInputBoxComponent(binding.item1.root)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return touchEventHandler.onTouchEvent(event)
    }
}
