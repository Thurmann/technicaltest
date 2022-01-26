package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.CardGame
import com.thurmann.technicaltest.model.cardgame.Player
import com.thurmann.technicaltest.model.cardgame.PlayerResult
import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard
import com.thurmann.technicaltest.model.simplecarddeck.simpleCard
import com.thurmann.technicaltest.model.util.splitInHalf

class BlackJackGame(
    private val predeterminedCards: MutableSet<String>? = null,
    private val playerName: String = "sam",
    val blackJackPlayer: Player = Player(playerName),
    val dealer: Player = Player("dealer"),
    val cardGame: CardGame = CardGame(blackJackPlayer, dealer)
) {

    fun playGame(): Player? {
        firstTwoTurns()
        val winner = checkAfterFirstTwoTurns()
        if (winner != null)
            return getWinnerAndPrintScore(winner)
        return takeTurns(true)
    }
    
    private fun firstTwoTurns() {
        takeTurn(blackJackPlayer)
        takeTurn(dealer)
        takeTurn(blackJackPlayer)
        takeTurn(dealer)
    }

    private fun checkAfterFirstTwoTurns(): Player? {
        val playerHand = blackJackPlayer.getScore()
        val dealerHand = dealer.getScore()
        if (playerHand == 21 && dealerHand == 21)
            return blackJackPlayer
        if (playerHand >= 22 || dealerHand >= 22) {
            if (dealerHand >= 22 && playerHand < 22)
                return blackJackPlayer
            return dealer
        }
        val playerResult = getCurrentPlayerResult(blackJackPlayer)
        if (playerResult == PlayerResult.WON)
            return blackJackPlayer
        if (playerResult == PlayerResult.LOST)
            return dealer
        return null
    }

    private fun getCurrentPlayerResult(player: Player): PlayerResult {
        val currentPlayerScore = player.getScore()
        if (currentPlayerScore == 21)
            return PlayerResult.WON
        if (currentPlayerScore > 21)
            return PlayerResult.LOST
        if (player == blackJackPlayer) {
            if (currentPlayerScore >= 17)
                return PlayerResult.ROUND_OVER
        } else if (dealer.getScore() > blackJackPlayer.getScore()) {
            return PlayerResult.ROUND_OVER
        }
        return PlayerResult.PENDING
    }
    
    private fun getWinnerAndPrintScore(winnerByResult: Player? = null): Player {
        val winner = winnerByResult ?: getWinner()
        printScore(winner)
        return winner
    }

    private fun getWinner(): Player {
        val winners = cardGame.players.filter { it.playerResult == PlayerResult.WON }
        if (winners.isNotEmpty())
            return winners.first()
        return cardGame.players.filter { it.playerResult != PlayerResult.LOST }
            .sortedBy { it.getScore() }.reversed()
            .first()
    }
    
    private fun printScore(winner: Player): String {
        val sb = StringBuilder()
        sb.appendLine(winner.name)
        for (player in cardGame.players) {
            sb.appendLine("${player.name}: ${player.hand}")
        }
        print(sb.toString())
        return sb.toString()
    }

    private fun takeTurns(firstTurnAfterFirstTwo: Boolean): Player? {
        for (player in cardGame.players) {
            val result = getCurrentPlayerResult(player)
            player.playerResult = result
            if (gameIsOver(player))
                return getWinnerAndPrintScore()
            if (cardGame.players.map { it.playerResult }
                    .all { it == PlayerResult.ROUND_OVER })
                return getWinnerAndPrintScore()
            if (player.participatingInGame()) {
                takeTurn(player)
            }
        }
        return takeTurns(false)
    }
    
    private fun takeTurn(player: Player) {
        val predeterminedCard = getNextPredeterminedCard()
        if (predeterminedCard == null) {
            player.addCardToHand(cardGame.drawCardFromDeck())
            return
        }
        player.addCardToHand(cardGame.drawPredeterminedCard(predeterminedCard))
    }

    private fun gameIsOver(player: Player): Boolean {
        val result = player.playerResult
        if (result == PlayerResult.LOST || result == PlayerResult.WON) {
            return true
        }
        return false
    }

    private fun getNextPredeterminedCard(): SimpleCard? {
        if (predeterminedCards != null && predeterminedCards.isNotEmpty()) {
            val cardString = predeterminedCards.first()
            predeterminedCards.remove(cardString)
            return simpleCard(cardString)
        }
        return null
    }
}