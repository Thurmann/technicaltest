package com.thurmann.technicaltest.model.cardgame

import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard
import com.thurmann.technicaltest.model.simplecarddeck.SimpleCardDeck
import com.thurmann.technicaltest.model.util.splitInHalf

class CardGame(vararg playerList: Player) {
    val players = playerList.toList()
    val cardDeck = SimpleCardDeck()

    fun drawCardFromDeck() =
        cardDeck.drawCardFromDeck()
    
    fun drawPredeterminedCard(card: SimpleCard) = 
        cardDeck.drawPredeterminedCard(card)
}