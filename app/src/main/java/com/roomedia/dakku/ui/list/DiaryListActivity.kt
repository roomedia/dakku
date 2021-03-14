package com.roomedia.dakku.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.databinding.ActivityDiaryListBinding
import com.roomedia.dakku.ui.list.adapter.WeeklyDiaryAdapter
import com.roomedia.dakku.util.splitByWeek
import java.text.SimpleDateFormat
import java.util.Locale

class DiaryListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDiaryListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // MARK: sorted dummy data
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dataset = listOf(
            simpleDateFormat.parse("2021-02-10")!!,
            simpleDateFormat.parse("2021-02-10")!!,
            simpleDateFormat.parse("2021-02-13")!!,
            simpleDateFormat.parse("2021-02-14")!!,
            simpleDateFormat.parse("2021-02-15")!!,
            simpleDateFormat.parse("2021-02-18")!!,
            simpleDateFormat.parse("2021-02-20")!!,
            simpleDateFormat.parse("2021-02-21")!!,
            simpleDateFormat.parse("2021-02-21")!!,
            simpleDateFormat.parse("2021-02-27")!!,
            simpleDateFormat.parse("2021-03-01")!!,
            simpleDateFormat.parse("2021-03-02")!!,
            simpleDateFormat.parse("2021-03-04")!!,
            simpleDateFormat.parse("2021-03-04")!!,
            simpleDateFormat.parse("2021-03-05")!!,
            simpleDateFormat.parse("2021-03-06")!!,
            simpleDateFormat.parse("2021-03-08")!!,
            simpleDateFormat.parse("2021-03-10")!!,
            simpleDateFormat.parse("2021-03-12")!!,
            simpleDateFormat.parse("2021-03-13")!!,
            simpleDateFormat.parse("2021-03-14")!!,
        ).splitByWeek()

        binding.weeklyRecyclerView.let {
            it.adapter = WeeklyDiaryAdapter(this, dataset)
        }
    }
}
