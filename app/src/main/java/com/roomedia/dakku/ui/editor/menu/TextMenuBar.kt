package com.roomedia.dakku.ui.editor.menu

import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.Observer
import com.roomedia.dakku.R
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView

class TextMenuBar(
    private val menuHandlersManager: MenuBarManager,
    private val fontSpinner: Spinner,
) {
    val isBold = ObservableBoolean()
    val isItalic = ObservableBoolean()
    val observableTextSize = menuHandlersManager.observableTextSize
    val observableTextColor = menuHandlersManager.observableTextColor

    private var alignIndex = 0
    val alignIcon = ObservableInt()

    init {
        initFontSpinnerItemSelectedListener()
        initSelectedStickerObserver()
    }

    private fun initFontSpinnerItemSelectedListener() {
        fontSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>, p1: View, position: Int, p3: Long) {
                onFont(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>) {
                onFont(0)
            }
        }
    }

    private fun initSelectedStickerObserver() {
        val observer = Observer<StickerView?> { stickerView ->
            if (stickerView !is StickerTextViewImpl) return@Observer

            isBold.set(stickerView.typeface.isBold)
            isItalic.set(stickerView.typeface.isItalic)

            fontSpinner.setSelection(stickerView.fontIndex)
            observableTextSize.set(stickerView.textSize)
            observableTextColor.set(stickerView.currentTextColor)

            alignIndex = Alignments.indexOf(stickerView.gravity)
            alignIcon.set(AlignmentIcons[alignIndex])
        }
        menuHandlersManager.addSelectedStickerObserver(observer)
    }

    fun onFont(position: Int) {
        menuHandlersManager.stickerTextViewImpl?.setStyle(
            fontIndex = position
        )
    }

    fun onSize(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.setSeekBarListener(ScaleListener::class)
    }

    fun onColor(view: View) {
        menuHandlersManager.selectMenu(view.id)
    }

    fun onAlign(view: View) {
        menuHandlersManager.selectMenu(view.id)
        alignIndex = (alignIndex + 1) % Alignments.size
        alignIcon.set(AlignmentIcons[alignIndex])
        menuHandlersManager.stickerTextViewImpl?.setStyle(
            textAlignment = Alignments[alignIndex]
        )
    }

    fun onBold(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.stickerTextViewImpl?.apply {
            isBold.set(!typeface.isBold)
            setStyle(
                textStyle = if (typeface.isBold) 0 else 1 + if (typeface.isItalic) 2 else 0,
            )
        }
    }

    fun onItalic(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.stickerTextViewImpl?.apply {
            isItalic.set(!typeface.isItalic)
            setStyle(
                textStyle = if (typeface.isBold) 1 else 0 + if (typeface.isItalic) 0 else 2,
            )
        }
    }

    fun onSpacing(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.setSeekBarListener(LineSpacingListener::class)
        menuHandlersManager.setSeekBarListener(LetterSpacingListener::class)
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
