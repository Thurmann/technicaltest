package com.thurmann.technicaltest.main

import com.thurmann.technicaltest.service.cardgame.BlackJackGame
import com.thurmann.technicaltest.service.file.FileReader

class Application

fun main(args: Array<String>) {
    try {
        val path = args[0]
        BlackJackGame(FileReader(path).readSingleLineDelimitedFile(",", true).toMutableSet())
            .playGame()
    } catch (fileException: Exception) {
        BlackJackGame().playGame()
    }
}