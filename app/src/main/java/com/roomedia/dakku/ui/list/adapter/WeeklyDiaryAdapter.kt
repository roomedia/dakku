package com.roomedia.dakku.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.databinding.RecyclerWeeklyDiaryListItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeeklyDiaryAdapter(context: Context, var dataset: List<List<Diary>>) :
    RecyclerView.Adapter<WeeklyDiaryAdapter.WeeklyViewHolder>() {

    private val localDateFormat = SimpleDateFormat(context.getString(R.string.locale_date_format), Locale.getDefault())

    inner class WeeklyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerWeeklyDiaryListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_weekly_diary_list_item, parent, false)
        return WeeklyViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        holder.binding.WeekTextView.text = toWeekString(dataset[position].first(), localDateFormat)
        holder.binding.DiaryRecyclerView.apply {
            adapter = DiaryAdapter(dataset[position])
        }
    }

    private fun toWeekString(diary: Diary, localeDateFormat: SimpleDateFormat): String {
        val first = (diary.calendar.clone() as Calendar).apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        }
        val last = (first.clone() as Calendar).apply {
            add(Calendar.DATE, 6)
        }
        return String.format("%s - %s", localeDateFormat.format(first.time), localeDateFormat.format(last.time))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
