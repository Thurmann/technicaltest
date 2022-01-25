package com.thurmann.technicaltest.model.enumcarddeck

import com.thurmann.technicaltest.model.enumcarddeck.CardSuit.*
import com.thurmann.technicaltest.model.indexedcollection.IndexMap
import com.thurmann.technicaltest.model.indexedcollection.IndexSet
import kotlin.random.Random

class CardDeck(val aceIsHighest: Boolean = true) {
    private val suits = initCardDeck()
    private val start =
        if (aceIsHighest) 2
        else 1
    private val end = start + 12

    private fun initCardDeck(): IndexMap<CardSuit, IndexSet<Int>> {
        val map = IndexMap<CardSuit, IndexSet<Int>>()
        map[CLUBS] = initCardSuit()
        map[HEARTS] = initCardSuit()
        map[DIAMONDS] = initCardSuit()
        map[SPADES] = initCardSuit()
        return map
    }

    private fun initCardSuit(): IndexSet<Int> {
        val set = IndexSet<Int>()
        for (n in start..end) {
            set.add(n)
        }
        return set
    }

    fun drawCard(): Pair<CardSuit, Int>? {
        val set = LinkedHashSet<Int>()
        val suitIndex = Random.nextInt(1, 4)
        val valueIndex = Random.nextInt(start, end)
        var tries = 10
        var retry = true
        var card: Pair<CardSuit, Int>? = null
        while (retry && tries > 0) {
            try {
                card = getCard(suitIndex, valueIndex)
                retry = false
            } catch (exception: KotlinNullPointerException) {
                retry = true
            }

        }
        return card
    }

    private fun getCard(suitIndex: Int, valueIndex: Int): Pair<CardSuit, Int> {
        val cardSuit = suits.getKeyFromIndex(suitIndex)
        val value = suits[cardSuit!!]!!.getValueFromIndex(suitIndex)!!
        return Pair(cardSuit, value)
    }
}