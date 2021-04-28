package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class CommonMenuHandlers(
    private val activity: DiaryEditorActivity,
    binding: ActivityDiaryEditorBinding,
) {

    private val selectedMenu = MutableLiveData<@IdRes Int>()
    private val selectedSticker = activity.selectedSticker
    private val frame = binding.diaryFrame

    val visibility = ObservableInt(View.VISIBLE)
    val menuHandlersVisibility = ObservableInt(0)
    private val layerListener = LayerListener(
        frame,
        binding.seekBarMenu.variableVerticalSeekBar,
        selectedSticker.value as? View
    )

    init {
        initSelectedMenu(binding)
        initSelectedSticker()

        binding.commonMenu.commonMenuHandlers = this
        binding.addMenu.addMenuHandlers = AddMenuHandlers(activity, frame, selectedMenu)

        val textMenuHandlers = TextMenuHandlers(
            activity as LifecycleOwner,
            binding,
            selectedMenu,
            selectedSticker,
        )
        binding.textMenu.textMenuHandlers = textMenuHandlers

        val observableTextColor = textMenuHandlers.observableTextColor
        binding.colorMenu.colorMenuHandlers = ColorMenuHandlers(
            activity as Context,
            binding.colorMenu,
            selectedSticker,
            observableTextColor,
        )
    }

    private fun initSelectedMenu(binding: ActivityDiaryEditorBinding) {
        selectedMenu.observe(activity) { menuId ->
            binding.seekBarMenu.verticalSeekBar.visibility = when (menuId) {
                binding.textMenu.sizeButton.id,
                binding.textMenu.spacingButton.id -> View.VISIBLE
                else -> View.GONE
            }
            binding.seekBarMenu.horizontalSeekBar.visibility = when (menuId) {
                binding.textMenu.spacingButton.id -> View.VISIBLE
                else -> View.GONE
            }
            binding.seekBarMenu.variableVerticalSeekBar.visibility = when (menuId) {
                binding.commonMenu.layerButton.id -> View.VISIBLE
                else -> View.GONE
            }
            binding.colorMenu.colorContainer.visibility = when (menuId) {
                binding.textMenu.textColorButton.id -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun initSelectedSticker() {
        selectedSticker.observe(activity) { stickerView ->
            val menuId = when (stickerView) {
                is StickerTextViewImpl -> R.id.textMenu
                else -> 0
            }
            menuHandlersVisibility.set(menuId)
            layerListener.setSticker(stickerView as? View)
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

    fun onAdd(view: View) {
        selectedMenu.value = view.id
        menuHandlersVisibility.set(R.id.addMenu)
    }

    fun onUndo() {
        TODO("not yet implemented")
    }

    fun onRedo() {
        TODO("not yet implemented")
    }

    fun onDelete(view: View) {
        selectedMenu.value = view.id
        menuHandlersVisibility.set(0)
        activity.deleteSelected()
    }

    fun onLayer(view: View) {
        selectedMenu.value = view.id
    }

    fun onDuplicate(view: View) {
        selectedMenu.value = view.id
        TODO("not yet implemented")
    }
}
