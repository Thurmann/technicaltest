package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.CardGame
import com.thurmann.technicaltest.model.cardgame.Player
import com.thurmann.technicaltest.model.cardgame.PlayerResult
import com.thurmann.technicaltest.model.cardgame.exceptions.CardNotDrawnException
import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard
import com.thurmann.technicaltest.model.util.splitInHalf

class BlackJackGameService(
    private val predeterminedCards: MutableSet<String>? = null,
    private val playerName: String = "sam",
    val blackJackPlayer: Player = Player(playerName),
    val dealer: Player = Player("dealer"),
    val cardGame: CardGame = CardGame(blackJackPlayer, dealer)
) {

    private fun getPlayerCondition(player: Player): PlayerResult {
        val isPlayer = player == blackJackPlayer
        val playerHand = blackJackPlayer.hand.value()
        val dealerHand = dealer.hand.value()
        if (player.hand.value() > 21)
            return PlayerResult.LOST
        if (isPlayer) {
            if (playerHand == 21 && dealerHand == 21)
                return PlayerResult.WON
            if (playerHand >= 22 && dealerHand >= 22)
                return PlayerResult.LOST
            if (playerHand >= 17)
                return PlayerResult.ROUND_OVER
        }
        if (!isPlayer) {
            if (dealerHand > playerHand) {
                return PlayerResult.ROUND_OVER
            }
        }
        return PlayerResult.PENDING
    }

    private fun takeTurn(player: Player, cardString: String? = null) {
        if (cardString == null) {
            player.getCard(drawCard())
            return
        }
        player.getCard(drawCard(cardString))
    }

    private fun getScore(player: Player): Float {
        return player.hand.value().toFloat()
    }

    private fun printScore(winner: Player): String {
        val sb = StringBuilder()
        sb.appendLine(winner)
        for (player in cardGame.players) {
            sb.append("${player.key.name}: ${player.key.hand}")
        }
        return sb.toString()
    }

    private fun drawCard(card: SimpleCard) =
        card

    fun playGame(): Player? {
        takeTurn(blackJackPlayer)
        takeTurn(dealer)
        takeTurn(blackJackPlayer)
        takeTurn(dealer)
        for (player in cardGame.players.keys) {
            if (gameIsOver(player))
                return endGame()
        }
        return takeTurns()
    }

    private fun endGame(): Player {
        val winner = getWinner()
        printScore(winner)
        return winner
    }

    private fun getWinner(): Player {
        return cardGame.players.keys.filter { !cardGame.hasPlayerLostGame(it) }
            .map { Pair(it, getScore(it)) }
            .sortedBy { pair -> pair.second }
            .first().first
    }

    private fun takeTurns(): Player? {
        for (player in cardGame.players.keys) {
            val result = getPlayerCondition(player)
            player.playerResult = result
            if (gameIsOver(player))
                return endGame()
            if (cardGame.players.keys.map { it.playerResult }
                    .all { it == PlayerResult.ROUND_OVER })
                return endGame()
            if (cardGame.isPlayerParticipating(player)) {
                val nextStartCondition = getNextPredeterminedCard()
                if (nextStartCondition != null) {
                    takeTurn(player, nextStartCondition)
                } else
                    takeTurn(player)
            }
        }
        return takeTurns()
    }

    private fun gameIsOver(player: Player): Boolean {
        val result = player.playerResult
        if (result == PlayerResult.ROUND_OVER)
            cardGame.deactivatePlayer(player)
        if (result == PlayerResult.LOST || result == PlayerResult.WON) {
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
    
    private fun getNextPredeterminedCard(): String? {
        if (predeterminedCards != null && predeterminedCards.isNotEmpty()) {
            val cardString = predeterminedCards.first()
            predeterminedCards.remove(cardString)
            return cardString
        }
        return null
    }
}