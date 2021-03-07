package com.roomedia.dakku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

data class C(
    val a: Any,
    val b: Any = 0,
    val c: Any
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
