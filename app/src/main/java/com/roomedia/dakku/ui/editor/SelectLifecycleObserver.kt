package com.roomedia.dakku.ui.editor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.roomedia.dakku.ui.editor.sticker.StickerImageViewImpl

class SelectImageResultContract : ActivityResultContract<String, Uri?>() {

    override fun createIntent(context: Context, type: String?): Intent {
        return Intent(Intent.ACTION_PICK).setType(type)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.data
            else -> null
        }
    }
}

class SelectLifecycleObserver(
    private val activity: DiaryEditorActivity,
    private val registry: ActivityResultRegistry,
) : DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, SelectImageResultContract()) {
            if (it == null) return@register
            (activity.selectedSticker as? StickerImageViewImpl)?.setImage(it)
        }
    }

    fun selectItem(type: String) {
        getContent.launch(type)
    }
}
