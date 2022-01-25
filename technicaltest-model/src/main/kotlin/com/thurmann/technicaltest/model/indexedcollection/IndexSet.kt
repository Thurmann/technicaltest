package com.thurmann.technicaltest.model.indexedcollection

class IndexSet<V> {
    val indexMap = mutableMapOf<Int, V>()
    val set = mutableSetOf<V>()
    var current = 0

    fun add(value: V): V? {
        set.add(value)
        indexMap[current++] = value
        return value
    }

    fun getValueFromIndex(index: Int) =
        indexMap[index]
    
    fun pop(index: Int): V? {
        val value = getValueFromIndex(index)
        indexMap.remove(index)
        set.remove(value)
        return value
    }

    fun size() = set.size
}