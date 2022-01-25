package com.thurmann.technicaltest.model.simplecarddeck

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
}