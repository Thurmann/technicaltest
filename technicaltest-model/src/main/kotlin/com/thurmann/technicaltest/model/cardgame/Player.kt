package com.thurmann.technicaltest.model.cardgame

import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard

class Player(val name: String) {
    val hand = Hand()
    var playerResult = PlayerResult.PENDING

    fun addCardToHand(card: SimpleCard) =
        hand.addCard(card)
    
    fun getScore()= 
        hand.value()

    fun participatingInGame() =
        playerResult == PlayerResult.PENDING
}