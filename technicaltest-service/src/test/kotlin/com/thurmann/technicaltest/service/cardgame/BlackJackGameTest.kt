package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.Player
import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import com.thurmann.technicaltest.model.simplecarddeck.simpleCard
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
        game.cardGame.drawPredeterminedCard(simpleCard("CK"))
        game.cardGame.drawPredeterminedCard(simpleCard("S7"))
        game.cardGame.drawPredeterminedCard(simpleCard("H6"))
        game.cardGame.drawPredeterminedCard(simpleCard("DJ"))
        game.cardGame.drawPredeterminedCard(simpleCard("D10"))
        game.cardGame.drawPredeterminedCard(simpleCard("S10"))
        assertThat(game.cardGame.cardDeck.size()).isEqualTo(46)
        Assertions.assertThatThrownBy { game.cardGame.drawPredeterminedCard(simpleCard("CK")) }
            .isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.cardGame.drawPredeterminedCard(simpleCard("PK")) }
            .isInstanceOf(CardNotDrawnException::class.java)
        Assertions.assertThatThrownBy { game.cardGame.drawPredeterminedCard(simpleCard("CF")) }
            .isInstanceOf(CardNotDrawnException::class.java)
    }
    
    @Test
    fun verifyOnlyTwoPredeterminedCards() {
        val cards = mutableSetOf("CQ", "D8")
        val game = BlackJackGame(cards)
        game.playGame()
        assertThat(game.blackJackPlayer.hand.value()).isGreaterThanOrEqualTo(10)
        assertThat(game.dealer.hand.value()).isGreaterThanOrEqualTo(8)
        assertThat(game.blackJackPlayer.hand.cards.size).isGreaterThanOrEqualTo(2)
        assertThat(game.dealer.hand.cards.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    fun verifyAllAces() {
        val cards = mutableSetOf("CA", "HA", "DA", "SA")
        assertResult(cards, false, 22, 22)
    }

    @Test
    fun verifyBothBlackJack() {
        val cards = mutableSetOf("CA", "HK", "DQ", "SA")
        assertResult(cards, true, 21, 21)
    }

    @Test
    fun `verify that game is over after all players can't draw more cards`() {
        val cards = mutableSetOf("C5", "H6", "D6", "S10", "S6", "C4")
        assertResult(cards, false, 17, 20)
    }

    @Test
    fun verifyDealerBustPlayerOver17() {
        val cards = mutableSetOf("C5", "H6", "D6", "S10", "S6", "C6")
        assertResult(cards, true, 17, 22)
    }

    @Test
    fun verifyDealerBustPlayerUnder17() {
        val cards = mutableSetOf("C10", "HA", "D6", "SA")
        assertResult(cards, true, 16, 22)
    }

    @Test
    fun verifyOver17AndLost() {
        val cards = mutableSetOf("C7", "H4", "D7", "S6", "S4", "D10")
        assertResult(cards, false, 18, 20)
    }

    @Test
    fun verifyOver17AndLostFromFile() {
        val path = "src/test/resources/cardgame/blackjacktest.txt"
        val cards = FileReader(path).readSingleLineDelimitedFile(",", true).toMutableSet()
        assertResult(cards, false, 18, 20)
    }

    @Test
    fun verifyExampleFromFile() {
        val path = "src/test/resources/cardgame/example.txt"
        val cards = FileReader(path).readSingleLineDelimitedFile(",", true).toMutableSet()
        assertResult(cards, true, 20, 23)
    }

    private fun assertResult(cards: MutableSet<String>, playerIsWinner: Boolean, playerScore: Int, dealerScore: Int) {
        val game = BlackJackGame(cards)
        val actualWinner = game.playGame()
        assertThat(game.blackJackPlayer.hand.value()).isEqualTo(playerScore)
        assertThat(game.dealer.hand.value()).isEqualTo(dealerScore)
        if (playerIsWinner)
            assertThat(actualWinner).isEqualTo(game.blackJackPlayer)
        else
            assertThat(actualWinner).isEqualTo(game.dealer)

    }
}