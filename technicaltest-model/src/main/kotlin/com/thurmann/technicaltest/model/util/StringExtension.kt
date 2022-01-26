package com.thurmann.technicaltest.model.util

fun String.splitInHalf(): Pair<String, String> {
    val half = length / 2
    return Pair(this.substring(0, half), this.substring(half, length))
}

fun String.removeWhiteSpaces() =
    this.filterNot { it.isWhitespace() }
