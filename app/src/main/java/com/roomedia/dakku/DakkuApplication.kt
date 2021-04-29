package com.roomedia.dakku

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class DakkuApplication : Application() {

    val mediaFolder by lazy { File("${externalMediaDirs.first().absolutePath}/") }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setMediaFolder()
    }

    fun setMediaFolder() {
        MimeTypes.values().forEach { mimeTypeEnum ->
            val folder = mimeTypeEnum.toPath()
            if (folder.exists()) {
                return@forEach
            }
            folder.mkdir()
            downloadPreset(mimeTypeEnum.mimeType, folder)
        }
    }

    private fun downloadPreset(mimeType: String, parent: File) {
        Firebase.storage.reference.child(mimeType).listAll().addOnSuccessListener { result ->
            result.items.forEach { imageRef ->
                imageRef.getFile(File(parent, imageRef.name))
            }
        }
    }

    companion object {
        lateinit var instance: DakkuApplication
    }
}
