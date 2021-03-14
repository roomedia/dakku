package com.roomedia.dakku.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiaryAdapter(private val dataset: List<Date>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    // MARK: for temporal diary title
    private val simpleDateFormat = SimpleDateFormat("yyMMdd", Locale.getDefault())

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerDiaryItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        // MARK: temporal diary title
        holder.binding.dateTextView.text = simpleDateFormat.format(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
