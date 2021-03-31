package com.roomedia.dakku.repository

import androidx.lifecycle.LiveData
import com.roomedia.dakku.persistence.DakkuDatabase
import com.roomedia.dakku.persistence.Sticker

class StickerRepository : CommonRepository<Sticker>() {
    override val dao = DakkuDatabase.instance.stickerDao()

    fun getFrom(diaryId: Int): LiveData<List<Sticker>> {
        return dao.getFrom(diaryId)
    }
}
