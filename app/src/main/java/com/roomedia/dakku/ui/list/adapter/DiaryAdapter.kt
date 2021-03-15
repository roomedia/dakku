package com.roomedia.dakku.ui.list.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.roomedia.dakku.R
import com.roomedia.dakku.data.Diary
import com.roomedia.dakku.databinding.RecyclerDiaryItemBinding

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
        // MARK: temporal password input box
        val editText = EditText(view.context)
        AlertDialog.Builder(view.context)
            .setTitle("비밀 일기 잠금 해제")
            .setMessage("비밀번호를 입력하세요!")
            .setView(editText)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                // MARK: temporal password checking
                if (editText.text.toString() != "1234") {
                    TODO("not yet impl. wrong password feedback")
                    return@setPositiveButton
                }
                view.isSelected = false
                view.contentDescription = view.context.getString(R.string.lock_off)
            }
            .show()
    }
}
