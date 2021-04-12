package com.roomedia.dakku.ui.editor

import android.content.Context
import android.graphics.Bitmap
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.drawToBitmap
import androidx.lifecycle.MutableLiveData
import com.roomedia.dakku.DakkuApplication
import com.roomedia.dakku.R
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.persistence.Sticker
import com.roomedia.dakku.repository.StickerRepository
import com.roomedia.dakku.ui.editor.sticker.StickerView
import com.roomedia.dakku.ui.util.CommonViewModel
import java.io.ByteArrayOutputStream
import java.util.Date

class StickerViewModel(private var diary: Diary?) : CommonViewModel<Sticker>() {
    override val repository = StickerRepository()
    val stickers = diary?.id?.let { repository.getFrom(it) }

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

    fun save(diaryFrame: FrameLayout) {
        if (isInit) return
        saveDiaryEntity(diaryFrame)
        saveThumbnail(diaryFrame)
        Toast.makeText(diaryFrame.context, R.string.save_diary_message, Toast.LENGTH_SHORT).show()
    }

    private fun saveDiaryEntity(diaryFrame: FrameLayout) {
        if (diary == null) {
            diary = Diary(Date().time)
        }

        DakkuApplication.instance.setMediaFolder()
        val stickers = diaryFrame.children.mapIndexed { zIndex, view ->
            (view as StickerView).toSticker(diary!!.id, zIndex)
        }.filterNotNull().toList().toTypedArray()
        repository.insertInto(diary!!, *stickers)
    }

    private fun saveThumbnail(diaryFrame: FrameLayout) {
        val stream = ByteArrayOutputStream()
        toBitmap(diaryFrame).compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val thumbnail = stream.toByteArray()
        diaryFrame.context.openFileOutput(diary!!.id.toString(), Context.MODE_PRIVATE).use {
            it.write(thumbnail)
        }
    }

    private fun toBitmap(diaryFrame: FrameLayout): Bitmap {
        diaryFrame.children.forEach { (it as StickerView).hidePrivacyContent() }
        val bitmap = diaryFrame.drawToBitmap()
        diaryFrame.children.forEach { (it as StickerView).showPrivacyContent() }
        return bitmap
    }
}
