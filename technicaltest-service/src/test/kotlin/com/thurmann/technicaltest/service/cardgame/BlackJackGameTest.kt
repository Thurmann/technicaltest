package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import com.thurmann.technicaltest.service.file.FileReader
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BlackJackGameTest {

    @Test
    fun testDrawSpecificCard() {
        val game = BlackJackGame()
        assertThat(game.cardGame.cardDeck.size()).isEqualTo(52)
        assertTrue(game.blackJackPlayer.hand.isEmpty())
        assertTrue(game.dealer.hand.isEmpty())
        game.drawCard("CK")
        game.drawCard("S7")
        game.drawCard("H6")
        game.drawCard("DJ")
        game.drawCard("D10")
        game.drawCard("S10")
        assertThat(game.cardGame.cardDeck.size()).isEqualTo(46)
        Assertions.assertThatThrownBy { game.drawCard("CK") }.isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.drawCard("PK") }.isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.drawCard("CF") }.isInstanceOf(CardNotDrawnException::class.java)
    }
    
    @Test
    fun verifyAllAces() {
        val cards = mutableSetOf("CA", "HA", "DA", "SA")
        val game = BlackJackGame(cards)
        val result = game.playGame()
        assertThat(result).isEqualTo(game.dealer)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(22)
        assertThat(game.dealer.hand.value()).isEqualTo(22)
    }
    
    @Test
    fun verifyBothBlackJack() {
        val cards = mutableSetOf("CA", "HK", "DQ", "SA")
        val game = BlackJackGame(cards)
        val result = game.playGame()
        assertThat(result).isEqualTo(game.blackJackPlayer)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(21)
        assertThat(game.dealer.hand.value()).isEqualTo(21)
    }
    
    @Test
    fun verifyOver17AndWin() {
        val cards = mutableSetOf("CA", "H6", "D6", "S10", "S6")
        val game = BlackJackGame(cards)
        val result = game.playGame()
        assertThat(result).isEqualTo(game.blackJackPlayer)
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(17)
        assertThat(game.dealer.hand.value()).isEqualTo(22)
    }
    
    @Test
    fun verifyOver17AndLost() {
        val cards = mutableSetOf("CA", "H4", "D7", "S6", "S2", "D8")
        val game = BlackJackGame(cards)
        val result = game.playGame()
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(18)
        assertThat(game.dealer.hand.value()).isEqualTo(20)
        assertThat(result).isEqualTo(game.dealer)
    }
    
    @Test
    fun verifyOver17AndLostFromFile() {
        val path3 = "src/test/resources/cardgame/blackjacktest.txt"
        val cards = FileReader(path3).readSingleLineDelimitedFile(",", true).toMutableSet()
        val game = BlackJackGame(cards)
        val result = game.playGame()
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(18)
        assertThat(game.dealer.hand.value()).isEqualTo(20)
        assertThat(result).isEqualTo(game.dealer)
    }
}