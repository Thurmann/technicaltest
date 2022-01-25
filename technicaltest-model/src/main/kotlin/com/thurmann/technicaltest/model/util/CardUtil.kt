package com.thurmann.technicaltest.model.util

import com.thurmann.technicaltest.model.enumcarddeck.CardSuit

fun decodeSuit(suit: String): CardSuit {
    return when(suit) {
        "C" -> CardSuit.CLUBS
        "H" -> CardSuit.HEARTS
        "D" -> CardSuit.DIAMONDS
        "S" -> CardSuit.SPADES
        else -> CardSuit.UNKNOWN
    }
}

fun decodeCardValue(cardString: String): Int {
    return when(cardString) {
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8
        "9" -> 9
        "10" -> 10
        "J" -> 10
        "Q" -> 10
        "K" -> 10
        "A" -> 11
        else -> -1
    }
}