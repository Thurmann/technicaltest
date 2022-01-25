package com.thurmann.technicaltest.model.indexedcollection

class IndexMap<T, V> {
    val indexMap = mutableMapOf<Int, T>()
    val map = mutableMapOf<T, V>()
    var current = 0

    fun put(key: T, value: V): V {
        map[key] = value
        indexMap[current++] = key
        return value
    }

    fun getKeyFromIndex(index: Int) =
        indexMap[index]
    
    fun getValue(key: T) =
        map[key]
    
    fun getValueFromIndex(index: Int) =
        map[getKeyFromIndex(index)]

    fun pop(index: Int): V? {
        val valueIndex = indexMap[index]
        val value = map[valueIndex]
        indexMap.remove(index)
        map.remove(valueIndex)
        return value
    }

    operator fun set(key: T, value: V) {
        this.put(key, value)
    }

    operator fun get(key: T) =
        map[key]
    
    fun size() = map.size
}