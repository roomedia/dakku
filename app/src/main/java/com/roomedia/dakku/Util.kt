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

fun normalize(
    value: Number,
    srcStart: Number,
    srcEnd: Number,
    dstStart: Number,
    dstEnd: Number
): Float {
    val srcSpan = srcEnd.toFloat() - srcStart.toFloat()
    val dstSpan = dstEnd.toFloat() - dstStart.toFloat()
    val ratio = dstSpan / srcSpan
    return (dstStart.toFloat() - ratio * srcStart.toFloat()) + ratio * value.toFloat()
}
