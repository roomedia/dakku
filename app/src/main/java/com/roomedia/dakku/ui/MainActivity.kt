package com.roomedia.dakku.ui

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.roomedia.dakku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                binding.inputBoxComponent.run {
                    setPosition()
                    setRelativeOrigin(event.x, event.y)
                }
                true
            }
            MotionEvent.ACTION_MOVE -> {
                binding.inputBoxComponent.translation(event.x, event.y)
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}
