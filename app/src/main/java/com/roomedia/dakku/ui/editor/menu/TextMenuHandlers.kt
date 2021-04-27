package com.roomedia.dakku.ui.editor.menu

import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class TextMenuHandlers(
    lifecycleOwner: LifecycleOwner,
    binding: ActivityDiaryEditorBinding,
    private val selectedSticker: MutableLiveData<StickerView?>,
) {
    private val verticalSeekBar = binding.seekBarMenu.verticalSeekBar
    private val horizontalSeekBar = binding.seekBarMenu.horizontalSeekBar
    private val fontSpinner = binding.textMenu.fontSpinner

    val isBold = ObservableBoolean()
    val isItalic = ObservableBoolean()

    val observableTextSize = ObservableFloat(45F)
    val observableTextColor = ObservableInt(0xFF000000.toInt())

    private val verticalSeekBarListener = MutableLiveData<OnSeekBarChangeListener>()
    private val horizontalSeekBarListener = MutableLiveData<OnSeekBarChangeListener>()

    private var alignIndex = 0
    val alignIcon = ObservableInt()

    init {
        fontSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                onFont(position)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                onFont(0)
            }
        }

        selectedSticker.observe(lifecycleOwner) { stickerView ->
            (stickerView as? StickerTextViewImpl)?.apply {
                isBold.set(typeface.isBold)
                isItalic.set(typeface.isItalic)

                fontSpinner.setSelection(fontIndex)
                observableTextSize.set(textSize)
                observableTextColor.set(currentTextColor)

                verticalSeekBarListener.value?.setSticker(stickerView)
                horizontalSeekBarListener.value?.setSticker(stickerView)

                alignIndex = Alignments.indexOf(gravity)
                alignIcon.set(AlignmentIcons[alignIndex])
            }
        }

        verticalSeekBarListener.observe(lifecycleOwner) { listener ->
            verticalSeekBar.apply {
                setOnSeekBarChangeListener(listener)
            }
        }
        horizontalSeekBarListener.observe(lifecycleOwner) { listener ->
            horizontalSeekBar.apply {
                setOnSeekBarChangeListener(listener)
            }
        }
    }

    fun onFont(position: Int) {
        (selectedSticker.value as? StickerTextViewImpl)?.setStyle(
            fontIndex = position
        )
    }

    fun onSize() {
        (selectedSticker.value as? StickerTextViewImpl)?.let { stickerView ->
            verticalSeekBarListener.value = ScaleListener(
                verticalSeekBar,
                stickerView,
                observableTextSize
            )
        }
    }

    fun onColor() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            // TODO: 2021/04/14 set slider VISIBLE/GONE for all button -> do when working on undo/redo
        }
    }

    fun onAlign() {
        alignIndex = (alignIndex + 1) % Alignments.size
        alignIcon.set(AlignmentIcons[alignIndex])
        val sticker = selectedSticker.value as? StickerTextViewImpl
        sticker?.gravity = Alignments[alignIndex]
    }

    fun onBold() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isBold.set(!typeface.isBold)
            var textStyle = if (typeface.isBold) 0 else 1
            textStyle += if (typeface.isItalic) 2 else 0
            setStyle(
                textStyle = textStyle,
            )
        }
    }

    fun onItalic() {
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isItalic.set(!typeface.isItalic)
            var textStyle = if (typeface.isBold) 1 else 0
            textStyle += if (typeface.isItalic) 0 else 2
            setStyle(
                textStyle = textStyle,
            )
        }
    }

    fun onSpacing() {
        (selectedSticker.value as? StickerTextViewImpl)?.let { stickerView ->
            // TODO: 2021/04/14 set slider VISIBLE/GONE for all button -> do when working on undo/redo
            verticalSeekBarListener.value = LineSpacingListener(verticalSeekBar, stickerView)
            horizontalSeekBarListener.value = LetterSpacingListener(horizontalSeekBar, stickerView)
        }
    }

    companion object {
        val Alignments = listOf(
            Gravity.START + Gravity.CENTER_VERTICAL,
            Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL,
            Gravity.END + Gravity.CENTER_VERTICAL,
        )
        val AlignmentIcons = listOf(
            R.drawable.ic_align_left,
            R.drawable.ic_align_center,
            R.drawable.ic_align_right,
        )
    }
}
