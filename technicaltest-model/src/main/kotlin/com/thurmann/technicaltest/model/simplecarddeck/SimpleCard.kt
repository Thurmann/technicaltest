package com.thurmann.technicaltest.model.simplecarddeck

import com.thurmann.technicaltest.model.util.decodeCardValue
import com.thurmann.technicaltest.model.util.decodeSuit
import com.thurmann.technicaltest.model.util.splitInHalf

class SimpleCard(val suit: String, val cardString: String) {
    
    val suitType = decodeSuit(suit)
    val cardValue = decodeCardValue(cardString)

    override fun toString(): String {
        return suit + cardString
    }

    override fun equals(other: Any?): Boolean {
        if (other is SimpleCard) {
            return suitType == other.suitType && cardString == other.cardString
        }
        return false
    }

    override fun hashCode(): Int {
        var result = suitType.hashCode()
        val valueResult = cardString.hashCode()
        result = 31 * result + valueResult
        return result
    }
}

fun simpleCard(cardString: String): SimpleCard {
    val suitAndCard = cardString.splitInHalf()
    return SimpleCard(suitAndCard.first, suitAndCard.second)
}