package com.roomedia.dakku.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerWeeklyDiaryListItemBinding
import com.roomedia.dakku.persistence.Diary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeeklyDiaryAdapter(context: Context) :
    RecyclerView.Adapter<WeeklyDiaryAdapter.WeeklyViewHolder>() {

    private var dataSource: List<Diary> = listOf()
    private var dataset: MutableList<List<Diary>> = mutableListOf()
    private val localDateFormat = SimpleDateFormat(context.getString(R.string.locale_date_format), Locale.getDefault())

    fun setDataSource(dataSource: List<Diary>) {
        this.dataSource = dataSource
        dataset.clear()
        dataset.addAll(dataSource.splitByWeek())
        notifyDataSetChanged()
    }

    fun setFiltering(isFilterBookmark: Boolean, isFilterLock: Boolean) {
        dataset.clear()
        dataset.addAll(
            dataSource.filterBookmark(isFilterBookmark)
                .filterLock(isFilterLock)
                .splitByWeek()
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_weekly_diary_list_item, parent, false)
        return WeeklyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        holder.binding.weekString = dataset[position].toWeekString(localDateFormat)
        holder.binding.DiaryRecyclerView.apply {
            adapter = DiaryAdapter(dataset[position])
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun List<Diary>.splitByWeek(): List<List<Diary>> {
        return mutableListOf<MutableList<Diary>>().also { weeks ->
            forEach { diary ->
                if (weeks.isNotEmpty() && weeks.last().last().isInSameWeek(diary)) {
                    weeks.last().add(diary)
                } else {
                    weeks.add(mutableListOf(diary))
                }
            }
        }
    }

    private fun List<Diary>.filterBookmark(isChecked: Boolean): List<Diary> {
        return if (isChecked) this.filter { it.bookmark } else this
    }

    private fun List<Diary>.filterLock(isChecked: Boolean): List<Diary> {
        return if (isChecked) this.filter { it.lock } else this
    }

    private fun List<Diary>.toWeekString(localeDateFormat: SimpleDateFormat): String {
        val first = first().toCalendar().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        }
        val last = (first.clone() as Calendar).apply {
            add(Calendar.DATE, 6)
        }
        return String.format(
            "%s - %s",
            localeDateFormat.format(first.time),
            localeDateFormat.format(last.time)
        )
    }

    inner class WeeklyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerWeeklyDiaryListItemBinding.bind(itemView)
    }
}
