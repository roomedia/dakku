package com.roomedia.dakku.ui.editor.menu

import android.view.View
import androidx.databinding.ObservableInt
import com.roomedia.dakku.ui.editor.DiaryEditorActivity

class CommonMenuHandlers(private val activity: DiaryEditorActivity) {

    var commonMenuVisibility = ObservableInt(View.GONE)
    var addMenuVisibility = ObservableInt(View.GONE)

    fun setVisibility(isVisible: Boolean) {
        if (isVisible) {
            commonMenuVisibility.set(View.VISIBLE)
        } else {
            commonMenuVisibility.set(View.GONE)
            addMenuVisibility.set(View.GONE)
        }
    }

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

    fun onDelete() {
        activity.deleteSelected()
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
