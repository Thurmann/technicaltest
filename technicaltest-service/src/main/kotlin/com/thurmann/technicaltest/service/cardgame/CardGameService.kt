package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.CardGame
import com.thurmann.technicaltest.model.cardgame.Player
import com.thurmann.technicaltest.model.cardgame.PlayerResult
import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard
import com.thurmann.technicaltest.model.util.splitInHalf

abstract class CardGameService(
    val cardGame: CardGame,
    val startCondition: MutableSet<String> = mutableSetOf()
) {
    abstract fun getPlayerCondition(player: Player): PlayerResult
    abstract fun initializeGame()
    abstract fun takeTurn(player: Player, cardString: String? = null)
    abstract fun getScore(player: Player): Float
    abstract fun printScore(winner: Player): String
    abstract fun drawCard(card: SimpleCard): SimpleCard
    fun gameOverOnFirstFailCondition(): Boolean = false
    fun gameOverOnFirstWinCondition(): Boolean = true

    fun playGame(): Player? {
        initializeGame()
        for (player in cardGame.players.keys) {
            if (gameIsOver(player))
                return getWinner()
        }
        return takeTurns()
    }

    private fun endGame(): Player {
        val winner = getWinner()
        printScore(winner)
        return winner
    }

    protected fun getWinner(): Player {
        return cardGame.players.keys.filter { !cardGame.hasPlayerLostGame(it) }
            .map { Pair(it, getScore(it)) }
            .sortedBy { pair -> pair.second }
            .first().first
    }

    private fun takeTurns(startCondition: MutableSet<String>? = null): Player? {
        for (player in cardGame.players.keys) {
            val result = getPlayerCondition(player)
            player.playerResult = result
            if (cardGame.players.keys.map { it.playerResult }
                    .all { it == PlayerResult.ROUND_OVER })
                return endGame()
            if (gameIsOver(player))
                return endGame()
            if (cardGame.isPlayerParticipating(player))
                takeTurn(player, startCondition)
        }
        return takeTurns()
    }

    private fun gameIsOver(player: Player): Boolean {
        val result = player.playerResult
        if (result == PlayerResult.ROUND_OVER)
            cardGame.deactivatePlayer(player)
        if (result == PlayerResult.LOST) {
            cardGame.removePlayer(player)
            if (gameOverOnFirstFailCondition())
                return true
        }
        if (result == PlayerResult.WON) {
            if (gameOverOnFirstWinCondition())
                return true
        }
        return false
    }

    fun drawCard() =
        drawCard(cardGame.cardDeck.drawCard())

    fun drawCard(cardString: String): SimpleCard {
        val suitAndCard = cardString.splitInHalf()
        val card = SimpleCard(suitAndCard.first, suitAndCard.second)
        if (cardGame.cardDeck.cards.remove(card))
            return drawCard(card)
        else
            throw CardNotDrawnException()
    }
}