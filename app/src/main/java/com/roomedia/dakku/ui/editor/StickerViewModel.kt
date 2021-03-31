package com.roomedia.dakku.ui.editor

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.repository.StickerRepository
import com.roomedia.dakku.ui.util.CommonViewModel

class StickerViewModel(private val diaryId: Long) : CommonViewModel<Sticker>() {
    override val repository = StickerRepository()
    val stickers = repository.getFrom(diaryId)
    val isEdit = MutableLiveData<Boolean>()

    fun onEdit() {
        isEdit.value = true
    }

    fun onSave(stickerViews: Sequence<View>) {
        isEdit.value = false
        save(stickerViews)
    }

    fun onBack(editCallback: () -> Unit, saveCallback: () -> Unit) {
        if (isEdit.value == true) {
            editCallback()
            return
        }
        saveCallback()
    }

    fun save(stickerViews: Sequence<View>) {
        val diary = if (diaryId == 0L) Diary() else null
        val stickers = stickerViews.mapIndexed { zIndex, it ->
            Sticker(
                diary?.id ?: diaryId,
                it.translationX,
                it.translationY,
                it.scaleX,
                it.scaleY,
                it.rotation,
                zIndex,
                (it as TextView).text.toString(),
                if (it.id != -1) it.id else 0
            )
        }
        diary?.let {
            repository.insertInto(it, *stickers.toList().toTypedArray())
            return
        }
        stickers.forEach {
            when (it.id) {
                0 -> insert(it)
                else -> update(it)
            }
        }
    }
}
