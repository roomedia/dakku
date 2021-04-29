package com.roomedia.dakku.persistence

import android.net.Uri
import androidx.core.net.toUri
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

    @TypeConverter
    fun stringToCharSequence(string: String?): CharSequence? {
        return string
    }

    @TypeConverter
    fun charSequenceToString(charSequence: CharSequence?): String? {
        return charSequence?.toString()
    }

    @TypeConverter
    fun stringToUri(string: String?): Uri? {
        return string?.toUri()
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
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
