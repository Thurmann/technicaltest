package com.thurmann.technicaltest.model.util

fun <T : Any> ArrayList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun <T : Any> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] 
    this[index1] = this[index2]
    this[index2] = tmp
}