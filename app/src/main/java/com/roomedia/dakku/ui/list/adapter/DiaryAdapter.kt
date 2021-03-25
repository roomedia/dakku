package com.roomedia.dakku.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding
import com.roomedia.dakku.model.DiaryViewModelHandlers

class DiaryAdapter(private val dataset: List<Diary>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerDiaryItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        val handlers = DiaryViewModelHandlers()
        holder.binding.handlers = handlers
        holder.binding.diary = dataset[position]
        if (dataset[position].bookmark) {
            handlers.toggleBookmark(holder.binding.bookmarkImageButton)
        }
        if (dataset[position].lock) {
            handlers.toggleLock(holder.binding.lockImageButton)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
