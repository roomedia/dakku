package com.roomedia.dakku.model.sticker

import android.app.Application
import com.roomedia.dakku.data.sticker.Sticker
import com.roomedia.dakku.data.sticker.StickerRepository
import com.roomedia.dakku.model.CommonViewModel

class StickerViewModel(application: Application) : CommonViewModel<Sticker>(application) {
    override val repository = StickerRepository(application)
}
