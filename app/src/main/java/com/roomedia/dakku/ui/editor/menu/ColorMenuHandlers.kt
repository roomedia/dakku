package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.widget.ImageButton
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import com.airbnb.paris.extensions.style
import com.airbnb.paris.extensions.tint
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.MenuColorBinding
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class ColorMenuHandlers(
    context: Context,
    binding: MenuColorBinding,
    private val selectedSticker: LiveData<StickerView?>,
    private val textColor: ObservableInt,
) {
    init {
        context.resources.getIntArray(R.array.colors).map {
            ImageButton(context).apply {
                id = it
                contentDescription = "#${Integer.toHexString(it)}"
                style {
                    add(R.style.ColorMenu)
                    tint(it)
                }
                setOnClickListener {
                    onColor(it.id)
                }
            }
        }.forEach {
            binding.colorContainer.addView(it)
        }
    }

    private fun onColor(color: Int) {
        textColor.set(color)
        (selectedSticker.value as? StickerTextViewImpl)?.setTextColor(color)
    }
}
