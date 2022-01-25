package com.thurmann.technicaltest.model.simplecarddeck

import com.thurmann.technicaltest.model.util.decodeCardValue
import com.thurmann.technicaltest.model.util.decodeSuit

class SimpleCard(val suit: String, val cardString: String) {
    val suitType = decodeSuit(suit)
    val cardValue = decodeCardValue(cardString)

    override fun toString(): String {
        return suit + cardString
    }

    override fun equals(other: Any?): Boolean {
        if (other is SimpleCard) {
            return suitType == other.suitType && cardValue == other.cardValue
        }
        return false
    }

    override fun hashCode(): Int {
        var result = suitType.hashCode()
        result = 31 * result + cardValue
        return result
    }
}