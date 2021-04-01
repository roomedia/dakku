package com.roomedia.dakku.ui.editor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.persistence.StickerType
import com.roomedia.dakku.ui.util.REQUEST
import com.roomedia.dakku.ui.util.RESPONSE
import com.roomedia.dakku.ui.util.showConfirmDialog

class DiaryEditorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryEditorBinding.inflate(layoutInflater) }
    private val stickerViewModel by lazy {
        val diaryId = intent.getLongExtra("diary_id", 0L)
        StickerViewModel(diaryId)
    }
    private var transformGestureDetector: TransformGestureDetector? = null

    private lateinit var editMenuItem: MenuItem
    private lateinit var saveMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initCommonMenu()
        initDiaryFrame()

        val commonMenuHandlers = CommonMenuHandlers()
        binding.commonMenuHandlers = commonMenuHandlers
        binding.commonMenu.commonMenuHandlers = commonMenuHandlers
        binding.addMenu.addMenuHandlers = AddMenuHandlers(binding.diaryFrame)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCommonMenu() {
        stickerViewModel.isEdit.observe(this) { isEdit ->
            editMenuItem.isVisible = !isEdit
            saveMenuItem.isVisible = isEdit
            binding.commonMenuHandlers?.setVisibility(isEdit)
            binding.diaryFrame.children.forEach {
                it.isEnabled = isEdit
            }
        }
    }

    private fun initDiaryFrame() {
        stickerViewModel.stickers.observe(this) { stickers ->
            stickers.map {
                when (it.type) {
                    StickerType.TEXT_VIEW -> StickerTextViewImpl(this, it)
                    else -> TODO("not yet implemented")
                }
            }.forEach {
                binding.diaryFrame.addView(it)
            }
            stickerViewModel.stickers.removeObservers(this)
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
        when (intent.getIntExtra("request_code", -1)) {
            REQUEST.NEW_DIARY.ordinal,
            REQUEST.NEW_TEMPLATE.ordinal,
            REQUEST.NEW_STICKER.ordinal -> {
                stickerViewModel.isEdit.value = true
            }
            else -> {
                stickerViewModel.isEdit.value = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.button_editor_edit -> stickerViewModel.onEdit()
            R.id.button_editor_save -> {
                binding.diaryFrame.children.map {
                    it as StickerView
                }.also {
                    stickerViewModel.onSave(it)
                }
                Toast.makeText(this, R.string.save_diary_message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        stickerViewModel.onBack(
            {
                showConfirmDialog(this) {
                    setResult(RESPONSE.ROLLBACK_DIARY.ordinal)
                    finish()
                }
            },
            {
                binding.diaryFrame.children.map {
                    it as StickerView
                }.also {
                    stickerViewModel.save(it)
                }
                transformGestureDetector = null
                finish()
            }
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return transformGestureDetector?.onTouchEvent(event) ?: false
    }
}
