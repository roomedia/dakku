package com.roomedia.dakku.ui.editor

import android.view.View
import androidx.databinding.ObservableInt

class CommonMenuHandlers {

    var commonMenuVisibility = ObservableInt(View.GONE)
    var addMenuVisibility = ObservableInt(View.GONE)

    fun onAdd() {
        addMenuVisibility.set(View.VISIBLE)
    }

    fun onUndo() {
        addMenuVisibility.set(View.GONE)
        TODO("not yet implemented")
    }

    fun onRedo() {
        TODO("not yet implemented")
    }

    fun onDelete(sticker: View) {
        TODO("not yet implemented")
    }

    fun onTranslation() {
        TODO("not yet implemented")
    }

    fun onRotation() {
        TODO("not yet implemented")
    }

    fun onScale() {
        TODO("not yet implemented")
    }

    fun onLayer() {
        TODO("not yet implemented")
    }

    fun onDuplicate() {
        TODO("not yet implemented")
    }
}