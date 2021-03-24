package com.roomedia.dakku.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.databinding.ActivityDiaryListBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.ui.list.adapter.WeeklyDiaryAdapter
import com.roomedia.dakku.util.REQUEST
import com.roomedia.dakku.util.filterBookmark
import com.roomedia.dakku.util.filterLock
import com.roomedia.dakku.util.splitByWeek
import java.text.SimpleDateFormat
import java.util.Locale

class DiaryListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryListBinding.inflate(layoutInflater) }
    // MARK: sorted dummy data
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val dataset = listOf(
        Diary(simpleDateFormat.parse("2021-02-10")!!, "210210"),
        Diary(simpleDateFormat.parse("2021-02-10")!!, "210210-2ㅜㅜ", lock = true),
        Diary(simpleDateFormat.parse("2021-02-13")!!, "210213 오늘의 일", bookmark = true),
        Diary(simpleDateFormat.parse("2021-02-14")!!, ".", lock = true),
        Diary(simpleDateFormat.parse("2021-02-15")!!, "asdf", bookmark = true),
        Diary(simpleDateFormat.parse("2021-02-18")!!, "210218"),
        Diary(simpleDateFormat.parse("2021-02-20")!!, "210220"),
        Diary(simpleDateFormat.parse("2021-02-21")!!, "210221"),
        Diary(simpleDateFormat.parse("2021-02-21")!!, "210227"),
        Diary(simpleDateFormat.parse("2021-02-27")!!, "뭐더라", lock = true),
        Diary(simpleDateFormat.parse("2021-03-01")!!, "ㅇㄴㅋㅋㅋㅋ", bookmark = true, lock = true),
        Diary(simpleDateFormat.parse("2021-03-02")!!, "210302"),
        Diary(simpleDateFormat.parse("2021-03-04")!!, "210304"),
        Diary(simpleDateFormat.parse("2021-03-04")!!, "?????"),
        Diary(simpleDateFormat.parse("2021-03-05")!!, "ㄹㅇㅋㅋ"),
        Diary(simpleDateFormat.parse("2021-03-06")!!, "ㅎㅇ"),
    )
    private val adapter by lazy { WeeklyDiaryAdapter(this, dataset.splitByWeek()) }
    private lateinit var bookmarkMenuItem: MenuItem
    private lateinit var lockMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.weeklyRecyclerView.adapter = adapter
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
            R.id.button_category_bookmark -> {
                if (item.isChecked) R.drawable.ic_bookmark_on else R.drawable.ic_bookmark_off
            }
            R.id.button_category_lock -> {
                if (item.isChecked) R.drawable.ic_lock_on else R.drawable.ic_lock_off
            }
            else -> null
        }?.also { iconId ->
            item.setIcon(iconId)
        }
        adapter.dataset = dataset
            .filterBookmark(bookmarkMenuItem.isChecked)
            .filterLock(lockMenuItem.isChecked)
            .splitByWeek()
        adapter.notifyDataSetChanged()
        return true
    }

    fun toEditorActivity(view: View) {
        val intent = Intent(this, DiaryEditorActivity::class.java).apply {
            putExtra("request_code", REQUEST.NEW_DIARY)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivityForResult(intent, REQUEST.NEW_DIARY.ordinal)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // TODO: 2021/03/24 processing editor result is not yet implemented
    }
}
