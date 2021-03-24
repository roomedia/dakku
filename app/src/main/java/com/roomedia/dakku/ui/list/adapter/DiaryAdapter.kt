package com.roomedia.dakku.ui.list.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding
import com.roomedia.dakku.ui.editor.DiaryEditorActivity
import com.roomedia.dakku.util.showPasswordOpenDialog
import com.roomedia.dakku.util.showPasswordUnlockDialog

class DiaryAdapter(private val dataset: List<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerDiaryItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_diary_item, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        holder.binding.thumbnailImageView.setOnClickListener {
            thumbnailDidClicked(it.context, dataset[position])
        }
        holder.binding.bookmarkImageButton.setOnClickListener {
            bookmarkButtonDidClicked(it)
            dataset[position].bookmark = !dataset[position].bookmark
        }
        holder.binding.lockImageButton.setOnClickListener {
            lockButtonDidClicked(it)
            dataset[position].lock = !dataset[position].lock
        }

        if (dataset[position].bookmark) {
            bookmarkButtonDidClicked(holder.binding.bookmarkImageButton)
        }
        if (dataset[position].lock) {
            lockButtonDidClicked(holder.binding.lockImageButton)
        }

        holder.binding.dateTextView.text = dataset[position].title
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun thumbnailDidClicked(context: Context, diary: Diary) {
        if (!diary.lock) {
            context.startActivity(Intent(context, DiaryEditorActivity::class.java))
            return
        }
        showPasswordOpenDialog(context) {
            context.startActivity(Intent(context, DiaryEditorActivity::class.java))
        }
    }

    private fun bookmarkButtonDidClicked(view: View) {
        view.isSelected = !view.isSelected
        view.contentDescription = if (view.isSelected) {
            R.string.bookmark_on
        } else {
            R.string.bookmark_off
        }.let { contentDescriptionId ->
            view.context.getString(contentDescriptionId)
        }
    }

    private fun lockButtonDidClicked(view: View) {
        if (!view.isSelected) {
            view.isSelected = true
            view.contentDescription = view.context.getString(R.string.lock_on)
            return
        }
        showPasswordUnlockDialog(view.context) {
            view.isSelected = false
            view.contentDescription = view.context.getString(R.string.lock_off)
        }
    }
}
