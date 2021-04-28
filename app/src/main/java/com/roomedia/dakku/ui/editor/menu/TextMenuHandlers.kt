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
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class TextMenuHandlers(
    lifecycleOwner: LifecycleOwner,
    binding: ActivityDiaryEditorBinding,
    private val selectedMenu: MutableLiveData<Int>,
    private val selectedSticker: MutableLiveData<StickerView?>,
) {
    private val verticalSeekBar = binding.seekBarMenu.verticalSeekBar
    private val horizontalSeekBar = binding.seekBarMenu.horizontalSeekBar
    private val fontSpinner = binding.textMenu.fontSpinner

    val isBold = ObservableBoolean()
    val isItalic = ObservableBoolean()

    val observableTextSize = ObservableFloat(50f)
    val observableTextColor = ObservableInt(0xFF000000.toInt())

    private var verticalSeekBarListener: OnSeekBarChangeListener? = null
    private var horizontalSeekBarListener: OnSeekBarChangeListener? = null

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

                verticalSeekBarListener?.setSticker(stickerView)
                horizontalSeekBarListener?.setSticker(stickerView)

                alignIndex = Alignments.indexOf(gravity)
                alignIcon.set(AlignmentIcons[alignIndex])
            }
        }
    }

    fun onFont(position: Int) {
        (selectedSticker.value as? StickerTextViewImpl)?.setStyle(
            fontIndex = position
        )
    }

    fun onSize(view: View) {
        selectedMenu.value = view.id
        (selectedSticker.value as? StickerTextViewImpl)?.let { stickerView ->
            verticalSeekBarListener = ScaleListener(
                verticalSeekBar,
                stickerView,
                observableTextSize
            )
        }
    }

    fun onColor(view: View) {
        selectedMenu.value = view.id
    }

    fun onAlign(view: View) {
        selectedMenu.value = view.id
        alignIndex = (alignIndex + 1) % Alignments.size
        alignIcon.set(AlignmentIcons[alignIndex])
        val sticker = selectedSticker.value as? StickerTextViewImpl
        sticker?.gravity = Alignments[alignIndex]
    }

    fun onBold(view: View) {
        selectedMenu.value = view.id
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isBold.set(!typeface.isBold)
            var textStyle = if (typeface.isBold) 0 else 1
            textStyle += if (typeface.isItalic) 2 else 0
            setStyle(
                textStyle = textStyle,
            )
        }
    }

    fun onItalic(view: View) {
        selectedMenu.value = view.id
        (selectedSticker.value as? StickerTextViewImpl)?.apply {
            isItalic.set(!typeface.isItalic)
            var textStyle = if (typeface.isBold) 1 else 0
            textStyle += if (typeface.isItalic) 0 else 2
            setStyle(
                textStyle = textStyle,
            )
        }
    }

    fun onSpacing(view: View) {
        selectedMenu.value = view.id
        (selectedSticker.value as? StickerTextViewImpl)?.let { stickerView ->
            verticalSeekBarListener = LineSpacingListener(verticalSeekBar, stickerView)
            horizontalSeekBarListener = LetterSpacingListener(horizontalSeekBar, stickerView)
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
