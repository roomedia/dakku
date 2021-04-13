package com.roomedia.dakku.ui.editor.menu

import android.view.View
import androidx.databinding.ObservableInt
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class CommonMenuHandlers(
    private val activity: DiaryEditorActivity,
    binding: ActivityDiaryEditorBinding,
) {

    private val selectedSticker = activity.selectedSticker
    private val frame = binding.diaryFrame

    val visibility = ObservableInt(View.VISIBLE)
    val addMenuVisibility = ObservableInt()
    val textMenuVisibility = ObservableInt()

    init {
        initSelectedSticker()
        initMenuVisibility()

        binding.commonMenu.commonMenuHandlers = this
        binding.addMenu.addMenuHandlers = AddMenuHandlers(activity, frame)
        binding.textMenu.textMenuHandlers = TextMenuHandlers(activity, selectedSticker)
    }

    private fun initSelectedSticker() {
        selectedSticker.observe(activity) { sticker ->
            initMenuVisibility()
            when (sticker) {
                null -> return@observe
                is StickerTextViewImpl -> textMenuVisibility.set(View.VISIBLE)
            }
        }
    }

    private fun initMenuVisibility() {
        listOf(addMenuVisibility, textMenuVisibility).forEach {
            it.set(View.GONE)
        }
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
