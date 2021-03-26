package com.roomedia.dakku.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.data.diary.Diary
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding
import com.roomedia.dakku.model.diary.DiaryViewModelHandlers

class DiaryAdapter(private val dataset: List<Diary>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        holder.binding.handlers = DiaryViewModelHandlers()
        holder.binding.diary = dataset[position]
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerDiaryItemBinding.bind(itemView)
    }
}
