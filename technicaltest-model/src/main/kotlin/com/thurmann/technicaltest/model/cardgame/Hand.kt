package com.thurmann.technicaltest.model.cardgame

import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard

class Hand {
    val cards = mutableListOf<SimpleCard>()
    
    fun addCard(card: SimpleCard) = 
        cards.add(card)
    
    fun isEmpty() = cards.isEmpty()
    
    fun value() = cards.sumOf { it.cardValue }

    override fun toString(): String {
        val sb = StringBuilder()
        for (card in cards) {
            sb.append(card)
            sb.append(" ")
        }
        return sb.toString()
    }
}