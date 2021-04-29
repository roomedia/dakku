package com.roomedia.dakku.ui.editor

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.MimeTypes
import com.roomedia.dakku.R
import com.roomedia.dakku.databinding.ActivityStickerListBinding
import java.io.File
import java.security.InvalidKeyException
import java.util.Date

class StickerListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStickerListBinding.inflate(layoutInflater) }
    private val mimeTypeEnum by lazy {
        val type = intent.getStringExtra("mime_types") ?: throw InvalidKeyException()
        MimeTypes.values().find { it.mimeType == type }!!
    }
    private val adapter by lazy { StickerAdapter(mimeTypeEnum.toFolder()) }

    private val observer = SelectLifecycleObserver(activityResultRegistry) { uri ->
        cloneContent(uri)
        adapter.addDataSource(uri)
    }

    private fun cloneContent(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)!!
        // TODO: change extension as extension of source content
        val outputFile = File(mimeTypeEnum.toPath(), "${Date().time}.jpg")
        val outputStream = outputFile.outputStream()

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.stickerRecyclerView.adapter = adapter
        lifecycle.addObserver(observer)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sticker_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.button_sticker_add -> observer.selectItem("${mimeTypeEnum.mimeType}/*")
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}
