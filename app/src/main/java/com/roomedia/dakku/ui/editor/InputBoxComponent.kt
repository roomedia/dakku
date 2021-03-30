package com.roomedia.dakku.ui.editor

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.roomedia.dakku.databinding.InputBoxComponentBinding
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class InputBoxComponent(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var isInitialized = false
    private val displayMetrics = context.resources.displayMetrics!!

    private lateinit var horizontalEnds: Pair<Float, Float>
    private lateinit var verticalEnds: Pair<Float, Float>

    private lateinit var translationFactor: Pair<Float, Float>
    private var rotationFactor: Float? = null
    private var scaleFactor: Pair<Float, Float>? = null

    private lateinit var baseOrigin: Pair<Float, Float>
    private var baseAngle: Float? = null
    private var baseSpan: Float? = null

    private val binding by lazy { InputBoxComponentBinding.bind(this) }

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
        binding.backgroundImageView.layout(l, t, r, b)
        binding.textView.layout(MARGIN, MARGIN, r - MARGIN, b - MARGIN)
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

    private fun initScale(delta: Delta) {
        baseSpan = sqrt(delta.x.pow(2) + delta.y.pow(2))
        scaleFactor = Pair(scaleX, scaleY)
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

    fun scale(delta: Delta, ratioLock: Boolean) {
        if (baseSpan == null) {
            initScale(delta)
            return
        }
        val span = sqrt(delta.x.pow(2) + delta.y.pow(2))
        val value = normalize(span - baseSpan!!, verticalEnds.second, 5F)
        if (ratioLock) {
            val ratio = scaleY / scaleX
            scaleX = scaleFactor!!.first + value
            scaleY = ratio * scaleX
        } else {
            scaleX = scaleFactor!!.first + normalize(x, horizontalEnds.second, 5F)
            scaleY = scaleFactor!!.second + normalize(y, verticalEnds.second, 5F)
        }
    }

    fun deinit() {
        rotationFactor = null
        scaleFactor = null
        baseAngle = null
        baseSpan = null
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

    private fun normalize(value: Float, srcLimit: Float, dstLimit: Float): Float {
        return value * dstLimit / srcLimit
    }
}
