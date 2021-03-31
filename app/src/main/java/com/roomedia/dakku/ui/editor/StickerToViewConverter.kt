package com.roomedia.dakku.ui.editor

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.ui.util.showEditTextDialog

fun Sticker.toStickerView(context: Context): View {
    return TextView(context).toStickerView(this)
}

private fun TextView.toStickerView(sticker: Sticker): TextView {
    return toStickerView().apply {
        id = sticker.id
        text = sticker.text
    }
}

private fun TextView.toStickerView(): TextView {
    return this.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.StickerTextView)
        } else {
            @Suppress("DEPRECATION")
            setTextAppearance(context, R.style.StickerTextView)
        }
        setOnClickListener {
            showEditTextDialog(context, text.toString()) { text = it }
        }
    }
}
