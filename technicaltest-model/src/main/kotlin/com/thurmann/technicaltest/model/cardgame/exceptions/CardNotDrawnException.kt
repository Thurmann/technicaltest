package com.thurmann.technicaltest.model.cardgame.exceptions

class CardNotDrawnException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception() {
}