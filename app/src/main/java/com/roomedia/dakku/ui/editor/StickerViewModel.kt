package com.roomedia.dakku.ui.editor

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.repository.StickerRepository
import com.roomedia.dakku.ui.util.CommonViewModel

class StickerViewModel(private val diaryId: Int) : CommonViewModel<Sticker>() {
    override val repository = StickerRepository()
    val stickers = repository.getFrom(diaryId)
    val isEdit = MutableLiveData<Boolean>()

    fun onEdit() {
        isEdit.value = true
    }

    fun onSave() {
        isEdit.value = false
    }

    fun onBack(editCallback: () -> Unit, saveCallback: () -> Unit) {
        if (isEdit.value == true) {
            editCallback()
            return
        }
        saveCallback()
    }

    fun save(stickers: Sequence<View>) {
        stickers.mapIndexed { zIndex, it ->
            Sticker(
                diaryId,
                it.translationX,
                it.translationY,
                it.scaleX,
                it.scaleY,
                it.rotation,
                zIndex,
                (it as TextView).text.toString(),
                if (it.id != -1) it.id else 0
            )
        }.forEach {
            insertOrUpdate(it)
        }
    }

    private fun insertOrUpdate(sticker: Sticker) {
        when (sticker.id) {
            0 -> insert(sticker)
            else -> update(sticker)
        }
    }
}
