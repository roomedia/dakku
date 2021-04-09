package com.roomedia.dakku

import android.app.Application
import java.io.File

class DakkuApplication : Application() {

    private val mimeTypes = listOf("image/", "video/")
    val mediaFolder by lazy { File("${externalMediaDirs.first().absolutePath}/") }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setMediaFolder() {
        mimeTypes.forEach { mimeType ->
            val folder = File(mediaFolder, mimeType)
            if (!folder.exists()) {
                folder.mkdir()
            }
        }
    }

    companion object {
        lateinit var instance: DakkuApplication
    }
}
