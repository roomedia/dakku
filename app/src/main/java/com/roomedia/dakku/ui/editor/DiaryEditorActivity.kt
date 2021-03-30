package com.roomedia.dakku.ui.editor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryEditorBinding
import com.roomedia.dakku.ui.component.TransformGestureDetector
import com.roomedia.dakku.util.REQUEST
import com.roomedia.dakku.util.RESPONSE
import com.roomedia.dakku.util.showConfirmDialog

class DiaryEditorActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryEditorBinding.inflate(layoutInflater) }
    private var transformGestureDetector: TransformGestureDetector? = null

    private var isEdit = false
    private lateinit var editMenuItem: MenuItem
    private lateinit var saveMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        CommonMenuHandlers().also {
            binding.commonMenuHandlers = it
            binding.commonMenu.commonMenuHandlers = it
        }
        binding.addMenu.addMenuHandlers = AddMenuHandlers(binding.diaryFrame)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            REQUEST.NEW_STICKER.ordinal -> onEditPressed()
            else -> {}
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.button_editor_edit -> onEditPressed()
            R.id.button_editor_save -> onSavePressed()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onEditPressed() {
        isEdit = true
        saveMenuItem.isVisible = true
        editMenuItem.isVisible = false
        transformGestureDetector = TransformGestureDetector(this)
        binding.commonMenuHandlers?.commonMenuVisibility?.set(View.VISIBLE)
    }

    private fun onSavePressed() {
        // TODO: 2021/03/24 save to db
        isEdit = false
        editMenuItem.isVisible = true
        saveMenuItem.isVisible = false
        transformGestureDetector = null

        binding.commonMenuHandlers?.commonMenuVisibility?.set(View.GONE)
        Toast.makeText(this, getString(R.string.editor_save), Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (!isEdit) {
            transformGestureDetector = null
            finish()
            return
        }
        showConfirmDialog(this) {
            setResult(RESPONSE.ROLLBACK_DIARY.ordinal)
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return transformGestureDetector?.onTouchEvent(event) ?: false
    }
}
