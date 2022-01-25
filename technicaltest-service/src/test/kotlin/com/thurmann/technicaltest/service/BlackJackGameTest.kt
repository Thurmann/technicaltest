package com.thurmann.technicaltest.service

import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import com.thurmann.technicaltest.service.cardgame.BlackJackGameService
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*

internal class BlackJackGameTest {

    @Test
    fun testDrawSpecificCard() {
        val game = BlackJackGameService()
        assertThat(game.deck.size()).isEqualTo(52)
        assertTrue(game.blackJackPlayer.hand.isEmpty())
        assertTrue(game.dealer.hand.isEmpty())
        game.drawCard("C", "K")
        game.drawCard("S", "7")
        game.drawCard("H", "6")
        game.drawCard("D", "J")
        assertThat(game.deck.size()).isEqualTo(48)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(19)
        assertThat(game.dealer.hand.value()).isEqualTo(18)
        assertThatThrownBy { game.drawCard("C", "K") }.isInstanceOf(CardNotDrawnException::class.java)
        assertThatThrownBy { game.drawCard("P", "K") }.isInstanceOf(CardNotDrawnException::class.java)
        assertThatThrownBy { game.drawCard("C", "F") }.isInstanceOf(CardNotDrawnException::class.java)
    }

    @Test
    fun testDrawCard() {
    }
}