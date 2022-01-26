package com.thurmann.technicaltest.model.simplecarddeck

import com.thurmann.technicaltest.model.util.swap

class SimpleCardDeck {
    val cards = initCards()

    private fun initCards(): MutableList<SimpleCard> {
        val mutableList = mutableListOf<SimpleCard>()
        val suits = setOf("C", "D", "H", "S")
        val cards = setOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
        for (suit in suits) {
            for (card in cards) {
                mutableList.add(SimpleCard(suit, card))
            }
        }
        return shuffle(mutableList)
    }
    
    private fun shuffle(mutableList: MutableList<SimpleCard>): MutableList<SimpleCard> {
        for (n in 0..51) {
            val range = 0..51
            val randomIndex2 = range.filter { it != n }.random()
            mutableList.swap(n, randomIndex2)
        }
        return mutableList
    }
    
    fun drawCard(): SimpleCard {
        return cards.last()
    }
    
    fun size() = cards.size
}