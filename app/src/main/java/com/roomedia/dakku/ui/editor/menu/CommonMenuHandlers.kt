package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.view.View
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import com.roomedia.dakku.R
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
    val menuHandlersVisibility = ObservableInt(0)
    private val observableTextSize = ObservableFloat(45F)
    private val observableTextColor = ObservableInt(0xFF000000.toInt())

    init {
        initSelectedSticker()
        binding.commonMenu.commonMenuHandlers = this
        binding.addMenu.addMenuHandlers = AddMenuHandlers(activity, frame)
        binding.textMenu.textMenuHandlers = TextMenuHandlers(
            activity as LifecycleOwner,
            binding.seekBarMenu,
            selectedSticker,
            observableTextSize,
            observableTextColor,
        )
        binding.colorMenu.colorMenuHandlers = ColorMenuHandlers(
            activity as Context,
            binding.colorMenu,
            selectedSticker,
            observableTextColor,
        )
    }

    private fun initSelectedSticker() {
        selectedSticker.observe(activity) { stickerView ->
            val menuId = when (stickerView) {
                is StickerTextViewImpl -> R.id.textMenu
                else -> return@observe
            }
            menuHandlersVisibility.set(menuId)
        }
    }

    fun setVisibility(isVisible: Boolean) {
        if (isVisible) {
            visibility.set(View.VISIBLE)
        } else {
            visibility.set(View.GONE)
            menuHandlersVisibility.set(0)
        }
    }

    fun onAdd() {
        menuHandlersVisibility.set(R.id.addMenu)
    }

    fun onUndo() {
        TODO("not yet implemented")
    }

    fun onRedo() {
        TODO("not yet implemented")
    }

    fun onDelete() {
        menuHandlersVisibility.set(0)
        activity.deleteSelected()
    }

    fun onLayer() {
        TODO("not yet implemented")
    }

    fun onDuplicate() {
        TODO("not yet implemented")
    }
}
