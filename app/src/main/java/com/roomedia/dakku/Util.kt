package com.roomedia.dakku

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
