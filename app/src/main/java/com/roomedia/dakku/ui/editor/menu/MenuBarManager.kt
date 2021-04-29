package com.roomedia.dakku.ui.editor.menu

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.roomedia.dakku.R
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView
import java.io.InvalidClassException
import kotlin.reflect.KClass

class MenuHandlersManager(private val activity: DiaryEditorActivity) {

    val context = activity as Context
    val requestActivity = activity.requestActivity

    private val selectedMenu = MutableLiveData<@IdRes Int>()
    private val selectedSticker = activity.selectedSticker
    val stickerTextViewImpl get() = selectedSticker.value as? StickerTextViewImpl

    private val diaryFrame = activity.binding.diaryFrame
    private val verticalSeekBar = activity.binding.seekBarMenu.verticalSeekBar
    private val horizontalSeekBar = activity.binding.seekBarMenu.horizontalSeekBar
    private val colorContainer = activity.binding.colorMenu.colorContainer

    private var verticalSeekBarListener: OnSeekBarChangeListener? = null
    private var horizontalSeekBarListener: OnSeekBarChangeListener? = null

    val visibility = ObservableInt(View.VISIBLE)
    val subMenuVisibility = ObservableInt(0)
    val observableTextSize = ObservableFloat(50f)
    val observableTextColor = ObservableInt(0x000000)

    init {
        initSelectedMenuObserver()
        initSelectedStickerObserver()
        initMenuHandlers()
    }

    private fun initSelectedMenuObserver() {
        selectedMenu.observe(activity) { menuId ->
            verticalSeekBar.visibility = when (menuId) {
                R.id.layerButton, R.id.sizeButton, R.id.spacingButton -> View.VISIBLE
                else -> View.GONE
            }
            horizontalSeekBar.visibility = when (menuId) {
                R.id.spacingButton -> View.VISIBLE
                else -> View.GONE
            }
            colorContainer.visibility = when (menuId) {
                R.id.textColorButton -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun initSelectedStickerObserver() {
        selectedSticker.observe(activity) { stickerView ->
            val menuId = when (stickerView) {
                is StickerTextViewImpl -> R.id.textMenu
                else -> 0
            }
            subMenuVisibility.set(menuId)

            if (stickerView == null) {
                verticalSeekBar.visibility = View.GONE
                horizontalSeekBar.visibility = View.GONE
                colorContainer.visibility = View.GONE
                return@observe
            }
            if (stickerView !is StickerTextViewImpl) {
                if (verticalSeekBarListener !is LayerListener) {
                    verticalSeekBar.visibility = View.GONE
                }
                horizontalSeekBar.visibility = View.GONE
                colorContainer.visibility = View.GONE
            }
            verticalSeekBarListener?.setSticker(stickerView)
            horizontalSeekBarListener?.setSticker(stickerView)
        }
    }

    fun addSelectedStickerObserver(observer: Observer<StickerView?>) {
        selectedSticker.observe(activity, observer)
    }

    private fun initMenuHandlers() {
        activity.binding.let {
            it.commonMenu.commonMenuHandlers = MainMenuHandlers(this)
            it.addMenu.addMenuHandlers = AddMenuBar(this, diaryFrame)
            it.textMenu.textMenuHandlers = TextMenuHandlers(this, it.textMenu.fontSpinner)
            it.colorMenu.colorMenuHandlers = ColorMenuHandlers(this, colorContainer)
        }
    }

    fun setVisibility(isVisible: Boolean) {
        if (isVisible) {
            visibility.set(View.VISIBLE)
        } else {
            visibility.set(View.GONE)
            subMenuVisibility.set(0)
        }
    }

    fun showSubMenu(@IdRes menuHandlersId: Int) {
        subMenuVisibility.set(menuHandlersId)
    }

    fun hideAllSubMenuHandlers() {
        subMenuVisibility.set(0)
    }

    fun selectMenu(@IdRes menuId: Int) {
        selectedMenu.value = menuId
    }

    fun selectSticker(stickerView: StickerView?) {
        stickerView?.setSelected(true)
        selectedSticker.value?.setSelected(false)
        selectedSticker.value = stickerView
    }

    fun deleteSelectedSticker() {
        selectedSticker.value?.let { sticker ->
            diaryFrame.removeView(sticker as View)
        }
        selectSticker(null)
    }

    fun setSeekBarListener(listenerType: KClass<*>) {
        when (listenerType) {
            ScaleListener::class -> {
                verticalSeekBarListener = ScaleListener(
                    verticalSeekBar,
                    stickerTextViewImpl!!,
                    observableTextSize
                )
            }
            LineSpacingListener::class -> {
                verticalSeekBarListener = LineSpacingListener(
                    verticalSeekBar,
                    stickerTextViewImpl!!
                )
            }
            LetterSpacingListener::class -> {
                horizontalSeekBarListener = LetterSpacingListener(
                    horizontalSeekBar,
                    stickerTextViewImpl!!
                )
            }
            LayerListener::class -> {
                verticalSeekBarListener = LayerListener(
                    diaryFrame,
                    verticalSeekBar,
                    selectedSticker.value!!
                )
            }
            else -> throw InvalidClassException("Listener Type must inherit interface OnSeekBarChangeListener!!")
        }
    }
}
