package com.roomedia.dakku.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.roomedia.dakku.R
import kotlin.math.PI
import kotlin.math.atan2

class InputBoxComponent(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var isInitialized = false
    private val displayMetrics = context.resources.displayMetrics!!

    private lateinit var horizontalEnds: Pair<Float, Float>
    private lateinit var verticalEnds: Pair<Float, Float>

    private lateinit var translationFactor: Pair<Float, Float>
    private var rotationFactor: Float? = null

    private lateinit var baseOrigin: Pair<Float, Float>
    private var baseAngle: Float? = null

    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val backgroundImageView by lazy { findViewById<ImageView>(R.id.backgroundImageView) }

    companion object {
        private const val MARGIN = 48
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!isInitialized) {
            isInitialized = true
            initTranslation(w, h)
        }
        setDisplayEnds(w.toFloat(), h.toFloat())
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val right: Int
        val bottom: Int
        backgroundImageView.drawable.run {
            right = intrinsicWidth
            bottom = intrinsicHeight
        }
        backgroundImageView.layout(0, 0, right, bottom)
        textView.layout(MARGIN, MARGIN, right - MARGIN, bottom - MARGIN)
    }

    private fun initTranslation(w: Int, h: Int) {
        displayMetrics.run {
            translationX = (widthPixels - w) / 2F
            translationY = (heightPixels - h) / 2F
        }
    }

    private fun setDisplayEnds(w: Float, h: Float) {
        displayMetrics.run {
            horizontalEnds = Pair(-w, widthPixels.toFloat())
            verticalEnds = Pair(-h, heightPixels.toFloat())
        }
    }

    fun initTranslation(x: Float, y: Float) {
        baseOrigin = Pair(x, y)
        translationFactor = Pair(translationX, translationY)
    }

    private fun initRotation(delta: Delta) {
        baseAngle = atan2(delta.y, delta.x)
        rotationFactor = rotation
    }

    fun translation(x: Float, y: Float) {
        val dx = x - baseOrigin.first
        val dy = y - baseOrigin.second
        translationX = clamp(translationFactor.first + dx, horizontalEnds)
        translationY = clamp(translationFactor.second + dy, verticalEnds)
    }

    fun rotation(delta: Delta) {
        if (baseAngle == null) {
            initRotation(delta)
            return
        }
        val angle = atan2(delta.y, delta.x)
        val relativeAngle = toDegrees(angle - baseAngle!!)
        rotation = rotationFactor!! + relativeAngle
    }

    fun scale(dw: Float, dh: Float) {
        TODO("Not yet implemented")
    }

    fun deinit() {
        rotationFactor = null
        baseAngle = null
    }

    private fun clamp(value: Float, ends: Pair<Float, Float>): Float {
        if (value < ends.first)
            return ends.first
        if (value > ends.second)
            return ends.second
        return value
    }

    private fun toDegrees(value: Float): Float {
        return (value / PI.toFloat() * 180F) % 360F
    }
}
