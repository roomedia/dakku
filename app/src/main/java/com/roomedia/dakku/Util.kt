package com.roomedia.dakku

const val LINE = 0
const val LETTER = 1

const val LINE_SPACING_START = 50
const val LINE_SPACING_END = 150

const val LETTER_SPACING_START = 0F
const val LETTER_SPACING_END = 1F

const val SLIDER_START = 0F
const val SLIDER_END = 100F

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

fun Number.spacingToSlider(type: Int): Int {
    val (srcStart, srcEnd) = if (type == LINE) {
        Pair(LINE_SPACING_START, LINE_SPACING_END)
    } else {
        Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, srcStart, srcEnd, SLIDER_START, SLIDER_END).toInt()
}

fun Number.sliderToSpacing(type: Int): Float {
    val (dstStart, dstEnd) = if (type == LINE) {
        Pair(LINE_SPACING_START, LINE_SPACING_END)
    } else {
        Pair(LETTER_SPACING_START, LETTER_SPACING_END)
    }
    return normalize(this, SLIDER_START, SLIDER_END, dstStart, dstEnd)
}
