package com.roomedia.dakku.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerWeeklyDiaryListItemBinding
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.util.filterBookmark
import com.roomedia.dakku.util.filterLock
import com.roomedia.dakku.util.splitByWeek
import com.roomedia.dakku.util.toWeekString
import java.text.SimpleDateFormat
import java.util.Locale

class WeeklyDiaryAdapter(context: Context) :
    RecyclerView.Adapter<WeeklyDiaryAdapter.WeeklyViewHolder>() {

    private var dataSource: List<Diary> = listOf()
    private var dataset: MutableList<List<Diary>> = mutableListOf()
    private val localDateFormat = SimpleDateFormat(context.getString(R.string.locale_date_format), Locale.getDefault())

    fun setDataSource(dataSource: List<Diary>) {
        // TODO: 2021/03/25 should be changed to DiffUtil method
        this.dataSource = dataSource
        dataset.clear()
        dataset.addAll(dataSource.splitByWeek())
        notifyDataSetChanged()
    }

    fun setFiltering(isFilterBookmark: Boolean, isFilterLock: Boolean) {
        // TODO: 2021/03/25 should be changed to DiffUtil method
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

    inner class WeeklyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerWeeklyDiaryListItemBinding.bind(itemView)
    }
}
