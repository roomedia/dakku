package com.roomedia.dakku.repository

import com.roomedia.dakku.persistence.DakkuDatabase
import com.roomedia.dakku.persistence.Sticker

class StickerRepository() : CommonRepository<Sticker>() {
    override val dao = DakkuDatabase.instance.stickerDao()
}
