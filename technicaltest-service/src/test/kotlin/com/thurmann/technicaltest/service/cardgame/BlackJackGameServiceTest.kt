package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BlackJackGameServiceTest {

    @Test
    fun testDrawSpecificCard() {
        val game = BlackJackGameService()
        assertThat(game.cardGame.cardDeck.size()).isEqualTo(52)
        assertTrue(game.blackJackPlayer.hand.isEmpty())
        assertTrue(game.dealer.hand.isEmpty())
        game.drawCard("CK")
        game.drawCard("S7")
        game.drawCard("H6")
        game.drawCard("DJ")
        assertThat(game.cardGame.cardDeck.size()).isEqualTo(48)
        Assertions.assertThatThrownBy { game.drawCard("CK") }.isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.drawCard("PK") }.isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.drawCard("CF") }.isInstanceOf(CardNotDrawnException::class.java)
    }
    
    @Test
    fun verifyAllAces() {
        val cards = mutableSetOf("CA", "HA", "DA", "SA")
        val game = BlackJackGameService(cards)
        val result = game.playGame()
        assertThat(result).isEqualTo(game.dealer)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(22)
        assertThat(game.dealer.hand.value()).isEqualTo(22)
    }
    
    @Test
    fun verifyBothBlackJack() {
        val cards = mutableSetOf("CA", "HK", "DQ", "SA")
        val game = BlackJackGameService(cards)
        val result = game.playGame()
        assertThat(result).isEqualTo(game.blackJackPlayer)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(21)
        assertThat(game.dealer.hand.value()).isEqualTo(21)
    }
}