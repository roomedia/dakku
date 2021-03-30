package com.roomedia.dakku.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityDiaryListBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.util.REQUEST
import com.roomedia.dakku.util.setIcon

class DiaryListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryListBinding.inflate(layoutInflater) }
    private val diaryListViewModel: DiaryListViewModel by viewModels()

    private val adapter by lazy { WeeklyDiaryAdapter(this) }
    private var bookmarkMenuItem: MenuItem? = null
    private var lockMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        binding.weeklyRecyclerView.adapter = adapter
    }

    private fun initRecyclerView() {
        diaryListViewModel.diaries.observe(this) {
            adapter.setDataSource(it)
            adapter.setFiltering(
                bookmarkMenuItem?.isChecked ?: false,
                lockMenuItem?.isChecked ?: false
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_diary_list, menu)
        bookmarkMenuItem = menu.findItem(R.id.button_category_bookmark)
        lockMenuItem = menu.findItem(R.id.button_category_lock)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = !item.isChecked
        when (item.itemId) {
            R.id.button_category_bookmark -> item.setIcon(R.drawable.ic_bookmark_on, R.drawable.ic_bookmark_off)
            R.id.button_category_lock -> item.setIcon(R.drawable.ic_lock_on, R.drawable.ic_lock_off)
            else -> return false
        }
        diaryListViewModel.updateAll()
        return true
    }

    override fun onPause() {
        super.onPause()
        diaryListViewModel.updateAll()
    }

    fun toEditorActivity(view: View) {
        val intent = Intent(view.context, DiaryEditorActivity::class.java).apply {
            putExtra("request_code", REQUEST.NEW_DIARY.ordinal)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivityForResult(intent, REQUEST.NEW_DIARY.ordinal)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO: 2021/03/24 processing editor result is not yet implemented
    }
}
