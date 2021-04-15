package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl

class CommonMenuHandlers(
    private val activity: DiaryEditorActivity,
    private val binding: ActivityDiaryEditorBinding,
) {

    private val selectedSticker = activity.selectedSticker
    private val frame = binding.diaryFrame

    val visibility = ObservableInt(View.VISIBLE)
    val menuHandlersVisibility = ObservableInt(0)
    val textColor = ObservableInt(Color.BLACK)

    init {
        initSelectedSticker()
        binding.commonMenu.commonMenuHandlers = this
        binding.addMenu.addMenuHandlers = AddMenuHandlers(activity, frame)
        binding.textMenu.textMenuHandlers = TextMenuHandlers(
            activity as LifecycleOwner,
            selectedSticker,
            textColor
        )
        binding.colorMenu.colorMenuHandlers = ColorMenuHandlers(
            activity as Context,
            binding.colorMenu,
            selectedSticker,
            textColor
        )
    }

    private fun initSelectedSticker() {
        selectedSticker.observe(activity) { sticker ->
            when (sticker) {
                null -> return@observe
                is StickerTextViewImpl -> {
                    menuHandlersVisibility.set(R.id.textMenu)
                    binding.verticalSeekBar.apply {
                        setOnSeekBarChangeListener(LineSpacingListener(sticker))
                        progress = sticker.lineHeight.toSlider(SliderType.LINE_SPACING)
                    }
                    binding.horizontalSeekBar.apply {
                        setOnSeekBarChangeListener(LetterSpacingListener(sticker))
                        progress = sticker.letterSpacing.toSlider(SliderType.LETTER_SPACING)
                    }
                }
            }
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
