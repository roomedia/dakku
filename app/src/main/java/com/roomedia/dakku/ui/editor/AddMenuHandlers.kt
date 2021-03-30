package com.roomedia.dakku.ui.editor

import android.text.InputType
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.roomedia.dakku.R
import com.roomedia.dakku.ui.util.showEditTextDialog

class AddMenuHandlers(private val frame: FrameLayout) {
    fun onText() {
        val sticker = TextView(frame.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE + InputType.TYPE_CLASS_TEXT

            setPadding(32)
            setText(R.string.example_text)
            showEditTextDialog(context, text.toString()) { this.text = it }

            setOnClickListener {
                showEditTextDialog(context, text.toString()) { this.text = it }
            }
        }
        frame.addView(sticker)
    }

    fun onImage() {
        TODO("not yet implemented")
    }

    fun onVideo() {
        TODO("not yet implemented")
    }

    fun onSwitch() {
        TODO("not yet implemented")
    }

    fun onRadio() {
        TODO("not yet implemented")
    }

    fun onCheck() {
        TODO("not yet implemented")
    }

    fun onSlider() {
        TODO("not yet implemented")
    }
}
