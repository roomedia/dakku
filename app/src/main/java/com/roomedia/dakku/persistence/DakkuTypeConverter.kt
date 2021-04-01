package com.roomedia.dakku.persistence

import androidx.room.TypeConverter

class DakkuTypeConverter {
    @TypeConverter
    fun ordinalToStickerType(ordinal: Int): StickerType {
        return StickerType.values()[ordinal]
    }

    @TypeConverter
    fun stickerTypeToOrdinal(stickerType: StickerType): Int {
        return stickerType.ordinal
    }
}

enum class StickerType {
    TEXT_VIEW,
    IMAGE_VIEW,
    VIDEO_VIEW,
    SWITCH,
    RADIO_BUTTON,
    CHECK_BOX,
    SLIDER,
}
