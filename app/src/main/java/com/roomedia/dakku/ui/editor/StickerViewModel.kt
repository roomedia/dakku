package com.roomedia.dakku.ui.editor

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.repository.StickerRepository
import com.roomedia.dakku.ui.util.CommonViewModel

class StickerViewModel(private val diaryId: Long) : CommonViewModel<Sticker>() {
    override val repository = StickerRepository()
    val stickers = repository.getFrom(diaryId)

    private var isInit = true
    val isEdit = MutableLiveData<Boolean>()

    fun onEdit() {
        isEdit.value = true
    }

    fun onSave() {
        isInit = false
        isEdit.value = false
    }

    fun onBack(editCallback: () -> Unit, saveCallback: () -> Unit) {
        if (isEdit.value == true) {
            editCallback()
            return
        }
        saveCallback()
    }

    fun save(views: Sequence<View>) {
        if (isInit) return
        val stickers = views.mapIndexed { zIndex, view ->
            (view as StickerView).toSticker(diaryId, zIndex)
        }.toList().toTypedArray()
        repository.insertInto(Diary(diaryId), *stickers)

        val context = views.first().context
        Toast.makeText(context, R.string.save_diary_message, Toast.LENGTH_SHORT).show()
    }
}
