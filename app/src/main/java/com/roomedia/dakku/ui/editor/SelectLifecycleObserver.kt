package com.roomedia.dakku.ui.editor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class SelectImageResultContract : ActivityResultContract<String, Uri?>() {

    override fun createIntent(context: Context, type: String?): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType(type)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.data
            else -> null
        }
    }
}

class SelectLifecycleObserver(
    private val registry: ActivityResultRegistry,
    private val callback: (Uri) -> Unit
) : DefaultLifecycleObserver {

    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, SelectImageResultContract()) {
            if (it == null) return@register
            callback(it)
        }
    }

    fun selectItem(type: String) {
        getContent.launch(type)
    }
}
