package com.roomedia.dakku.ui.editor

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.editor.menu.CommonMenuHandlers
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerTextViewImpl
import com.roomedia.dakku.ui.editor.sticker.StickerView
import com.roomedia.dakku.ui.util.REQUEST
import com.roomedia.dakku.ui.util.showConfirmDialog
import java.util.Date

class DiaryEditorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryEditorBinding.inflate(layoutInflater) }
    private val stickerViewModel by lazy {
        val diary = intent.getParcelableExtra("diary") as? Diary ?: Diary(Date().time)
        StickerViewModel(diary)
    }

    private val transformGestureDetector by lazy { TransformGestureDetector.getInstance(this) }
    val selectedSticker by lazy { transformGestureDetector.selectedSticker }

    val requestActivity = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val uri = result.data?.data
        (selectedSticker.value as? StickerImageViewImpl)?.setImage(uri)
    }

    private lateinit var editMenuItem: MenuItem
    private lateinit var saveMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setIsEditObserver()
        addDiaryStickers()

        binding.commonMenuHandlers = CommonMenuHandlers(this, binding)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setIsEditObserver() {
        stickerViewModel.isEdit.observe(this) { isEdit ->
            editMenuItem.isVisible = !isEdit
            saveMenuItem.isVisible = isEdit

            transformGestureDetector.setEnable(isEdit)
            binding.commonMenuHandlers?.setVisibility(isEdit)
            if (!isEdit) {
                selectSticker(null)
                stickerViewModel.save(binding.diaryFrame)
            }
        }
    }

    private fun addDiaryStickers() {
        stickerViewModel.stickers?.observe(this) { stickers ->
            stickers.map {
                when (it.type) {
                    StickerType.TEXT_VIEW -> StickerTextViewImpl(this, it)
                    StickerType.IMAGE_VIEW -> StickerImageViewImpl(this, it)
                    else -> TODO("not yet implemented")
                }
            }.forEach {
                it.isEnabled = false
                binding.diaryFrame.addView(it)
            }
            stickerViewModel.stickers?.removeObservers(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_diary_editor, menu)
        editMenuItem = menu.findItem(R.id.button_editor_edit)
        saveMenuItem = menu.findItem(R.id.button_editor_save)
        initMode()
        return true
    }

    private fun initMode() {
        stickerViewModel.isEdit.value =
            when (intent.getIntExtra("request_code", -1)) {
                REQUEST.NEW_DIARY.ordinal,
                REQUEST.NEW_TEMPLATE.ordinal,
                REQUEST.NEW_STICKER.ordinal -> true
                else -> false
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.button_editor_edit -> stickerViewModel.onEdit()
            R.id.button_editor_save -> stickerViewModel.onSave()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        stickerViewModel.onBack(
            {
                showConfirmDialog(this) {
                    finish()
                }
            },
            { finish() }
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return transformGestureDetector.onTouchEvent(event)
    }

    fun selectSticker(stickerView: StickerView?) {
        stickerView?.setSelected(true)
        selectedSticker.value?.setSelected(false)
        selectedSticker.value = stickerView
    }

    fun deleteSelected() {
        selectedSticker.value?.let { sticker ->
            binding.diaryFrame.removeView(sticker as View)
        }
        selectSticker(null)
    }
}
