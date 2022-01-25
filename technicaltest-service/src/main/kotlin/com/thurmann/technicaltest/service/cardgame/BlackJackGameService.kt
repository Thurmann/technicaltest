package com.thurmann.technicaltest.service.cardgame

import com.thurmann.technicaltest.model.cardgame.CardGame
import com.thurmann.technicaltest.model.cardgame.Player
import com.thurmann.technicaltest.model.cardgame.PlayerResult
import com.thurmann.technicaltest.model.simplecarddeck.SimpleCard

class BlackJackGameService(
    private val playerName: String = "sam",
    val blackJackPlayer: Player = Player(playerName),
    val dealer: Player = Player("dealer"),
    cardGame: CardGame = CardGame(blackJackPlayer, dealer)
) : CardGameService(cardGame) {

    override fun getPlayerCondition(player: Player): PlayerResult {
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

    override fun initializeGame() {
        if (startCondition.isNotEmpty()) {
            for (cardString in startCondition) {
                takeTurn(cardGame.playerInTurn, cardString)
            }
        } else {
            for (turn in 1..4) {
                takeTurn(cardGame.playerInTurn)
            }
        }
    }

    override fun takeTurn(player: Player, cardString: String?) {
        if (cardString == null) {
            player.getCard(drawCard())
            return
        }
        player.getCard(drawCard(cardString))
    }

    override fun getScore(player: Player): Float {
        return player.hand.value().toFloat()
    }

    override fun printScore(winner: Player): String {
        val sb = StringBuilder()
        sb.appendLine(winner)
        for (player in cardGame.players) {
            sb.append("${player.key.name}: ${player.key.hand}")
        }
        return sb.toString()
    }

    override fun drawCard(card: SimpleCard) =
        card

}