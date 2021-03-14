package com.roomedia.dakku.ui.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerWeeklyDiaryListItemBinding
import com.roomedia.dakku.util.toWeekString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeeklyDiaryAdapter(context: Context, private val dataset: List<List<Date>>) :
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
        holder.binding.WeekTextView.text = dataset[position].first().toWeekString(localDateFormat)
        holder.binding.DiaryRecyclerView.apply {
            adapter = DiaryAdapter(dataset[position])
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
