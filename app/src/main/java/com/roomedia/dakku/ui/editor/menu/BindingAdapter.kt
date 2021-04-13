package com.roomedia.dakku.ui.editor.menu

import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("app:srcCompat")
    @JvmStatic fun ImageButton.setSrcCompat(@DrawableRes resId: Int) {
        setImageResource(resId)
    }
}
