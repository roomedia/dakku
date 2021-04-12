package com.roomedia.dakku.ui.editor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.roomedia.dakku.DakkuApplication
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.RecyclerStickerItemBinding
import java.io.File

class StickerAdapter(type: String) : RecyclerView.Adapter<StickerAdapter.StickerViewHolder>() {

    private var dataset = File(DakkuApplication.instance.mediaFolder, type).listFiles()
        ?.map { it.toUri() }
        ?.toMutableList()
        ?: mutableListOf()

    fun addDataSource(uri: Uri) {
        this.dataset.add(0, uri)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_sticker_item, parent, false)
        return StickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        holder.binding.imageView.apply {
            load(dataset[position]) {
                allowHardware(false)
            }
            setOnClickListener {
                val intent = Intent().apply {
                    data = dataset[position]
                }
                (context as Activity).apply {
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    inner class StickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RecyclerStickerItemBinding.bind(itemView)
    }
}
