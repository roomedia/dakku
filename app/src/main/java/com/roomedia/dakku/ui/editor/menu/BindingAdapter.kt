package com.roomedia.dakku.ui.editor.menu

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.airbnb.paris.extensions.backgroundTint
import com.airbnb.paris.extensions.style

@BindingMethods(
    BindingMethod(
        type = View::class,
        attribute = "android:selectVisibility",
        method = "selectVisibility"
    )
)
object BindingAdapters {
    @BindingAdapter("app:srcCompat")
    @JvmStatic fun ImageButton.setSrcCompat(@DrawableRes resId: Int) {
        setImageResource(resId)
    }

    @BindingAdapter("android:selectVisibility")
    @JvmStatic fun View.selectVisibility(visibleMenu: Int) {
        visibility = if (visibleMenu == id) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @BindingAdapter("android:backgroundTint")
    @JvmStatic fun Button.setBackgroundTint(isTint: Boolean) {
        style {
            val color = if (isTint) Color.LTGRAY else Color.WHITE
            backgroundTint(color)
        }
    }
}
