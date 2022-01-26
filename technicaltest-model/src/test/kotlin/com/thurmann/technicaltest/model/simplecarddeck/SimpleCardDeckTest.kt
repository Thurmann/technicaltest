package com.thurmann.technicaltest.model.simplecarddeck

import com.thurmann.technicaltest.model.enumcarddeck.CardSuit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SimpleCardDeckTest {
    
    @Test
    fun verifyShuffle() {
        val firstDeck = SimpleCardDeck()
        val secondDeck = SimpleCardDeck()
        val thirdDeck = SimpleCardDeck()
        println(firstDeck.cards)
        println(secondDeck.cards)
        assertThat(firstDeck.cards).isNotEqualTo(secondDeck.cards)
        assertThat(firstDeck.cards).isNotEqualTo(thirdDeck.cards)
        assertThat(secondDeck.cards).isNotEqualTo(thirdDeck.cards)
    }
    
    @Test
    fun verifyCardEquals() {
        assertFalse(SimpleCard("C", "K") == SimpleCard("D", "K"))
        assertFalse(SimpleCard("H", "K") == SimpleCard("D", "K"))
        assertTrue(SimpleCard("H", "K") == SimpleCard("H", "K"))
    }
}