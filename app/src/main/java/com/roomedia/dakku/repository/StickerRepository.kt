package com.roomedia.dakku.repository

import androidx.lifecycle.LiveData
import com.roomedia.dakku.persistence.DakkuDatabase
import com.roomedia.dakku.persistence.Diary
import com.roomedia.dakku.persistence.Sticker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StickerRepository : CommonRepository<Sticker>() {
    override val dao = DakkuDatabase.instance.stickerDao()

    fun getFrom(diaryId: Long): LiveData<List<Sticker>> {
        return dao.getFrom(diaryId)
    }

    fun insertInto(diary: Diary, vararg stickers: Sticker) {
        GlobalScope.launch {
            dao.insertInto(diary, *stickers)
        }
    }
}
