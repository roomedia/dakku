package com.roomedia.dakku.ui.editor

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

    fun onSave(stickerViews: Sequence<StickerView>) {
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

    fun save(stickerViews: Sequence<StickerView>) {
        val diary = if (diaryId == 0L) Diary() else null
        val stickers = stickerViews.mapIndexed { zIndex, stickerView ->
            stickerView.toSticker(diary?.id ?: diaryId, zIndex)
        }.toList().toTypedArray()

        diary?.let {
            repository.insertInto(it, *stickers)
        } ?: run {
            stickers.forEach { if (it.id == 0) insert(it) else update(it) }
        }
    }
}
