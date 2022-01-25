package com.thurmann.technicaltest.model.cardgame

import com.thurmann.technicaltest.model.simplecarddeck.SimpleCardDeck

class CardGame(vararg playerList: Player) {
    val players = initializePlayerList(playerList)
    val cardDeck = SimpleCardDeck()
    var playerInTurn = players.keys.first()

    private fun initializePlayerList(playerList: Array<out Player>): MutableMap<Player, PlayerResult> {
        val mutableMap = mutableMapOf<Player, PlayerResult>()
        for (player in playerList) {
            mutableMap[player] = PlayerResult.PENDING
        }
        return mutableMap
    }

    fun removePlayer(player: Player) {
        players[player] = PlayerResult.LOST
    }

    fun deactivatePlayer(player: Player) {
        players[player] = PlayerResult.ROUND_OVER
    }

    fun isPlayerParticipating(player: Player) =
        players[player] == PlayerResult.PENDING

    fun hasPlayerLostGame(player: Player) =
        players[player] == PlayerResult.LOST

}