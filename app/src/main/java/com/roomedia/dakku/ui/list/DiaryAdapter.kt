package com.roomedia.dakku.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding
import com.roomedia.dakku.persistence.Diary

class DiaryAdapter(private val dataset: List<Diary>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.binding.viewModel = DiaryViewModel(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerDiaryItemBinding.bind(itemView)
    }
}
