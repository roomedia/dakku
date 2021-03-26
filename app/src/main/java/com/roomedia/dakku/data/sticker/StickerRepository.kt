package com.roomedia.dakku.data.sticker

import android.content.Context
import com.roomedia.dakku.data.CommonRepository
import com.roomedia.dakku.util.DiaryDatabase

class StickerRepository(applicationContext: Context) : CommonRepository<Sticker>() {
    override val dao = DiaryDatabase.getInstance(applicationContext).stickerDao()
}
