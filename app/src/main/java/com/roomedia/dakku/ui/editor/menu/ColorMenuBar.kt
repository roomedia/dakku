package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.widget.ImageButton
import android.widget.LinearLayout
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.tint
import com.roomedia.dakku.R

class ColorMenuBar(
    private val menuHandlersManager: MenuBarManager,
    private val colorContainer: LinearLayout,
) {

    private val observableTextColor = menuHandlersManager.observableTextColor

    init {
        with(menuHandlersManager.context) {
            resources.getIntArray(R.array.colors)
                .map { initColorButton(this, it) }
                .forEach { colorContainer.addView(it) }
        }
    }

    private fun initColorButton(context: Context, color: Int): ImageButton {
        return ImageButton(context).apply {
            id = color
            contentDescription = "#${Integer.toHexString(color)}"
            style {
                add(R.style.ColorMenu)
                tint(color)
            }
            setOnClickListener {
                onColor(it.id)
            }
        }
    }

    private fun onColor(color: Int) {
        observableTextColor.set(color)
        menuHandlersManager.stickerTextViewImpl?.setTextColor(color)
    }
}
