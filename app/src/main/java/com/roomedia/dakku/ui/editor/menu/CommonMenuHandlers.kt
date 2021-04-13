package com.roomedia.dakku.ui.editor.menu

import android.util.Log
import android.view.View
import androidx.databinding.ObservableInt
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerTextView

class CommonMenuHandlers(
    private val activity: DiaryEditorActivity,
    binding: ActivityDiaryEditorBinding,
) {

    private val selectedSticker = activity.selectedSticker
    private val frame = binding.diaryFrame

    var visibility = ObservableInt(View.VISIBLE)
    var addMenuVisibility = ObservableInt()

    init {
        initSelectedSticker()
        initMenuVisibility()

        binding.commonMenu.commonMenuHandlers = this
        binding.addMenu.addMenuHandlers = AddMenuHandlers(activity, frame)
    }

    private fun initSelectedSticker() {
        selectedSticker.observe(activity) { sticker ->
            when (sticker) {
                null -> initMenuVisibility()
                is StickerTextView -> Log.d("SELECT", "select textView")
            }
        }
    }

    private fun initMenuVisibility() {
        addMenuVisibility.set(View.GONE)
    }

    fun setVisibility(isVisible: Boolean) {
        if (isVisible) {
            visibility.set(View.VISIBLE)
        } else {
            visibility.set(View.GONE)
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
