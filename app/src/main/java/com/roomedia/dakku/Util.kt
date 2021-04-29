package com.roomedia.dakku

import java.io.File

enum class MimeTypes(val mimeType: String) {
    Image("image/"),
    Video("video/");

    fun toFolder() = DakkuApplication.instance.run {
        resources.getString(R.string.media_path, mimeType)
    }

    fun toPath() = DakkuApplication.instance.run {
        File(mediaFolder, toFolder())
    }
}
