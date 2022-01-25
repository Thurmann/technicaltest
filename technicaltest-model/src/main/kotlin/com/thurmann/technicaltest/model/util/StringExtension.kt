package com.thurmann.technicaltest.model.util

fun String.splitInHalf(): Pair<String, String> {
    val half = length / 2
    if(length % 2 == 1)
        return Pair(this.substring(0, half + 1), this.substring(half + 1, length))
    return Pair(this.substring(0, half), this.substring(half, length))
}