package characters

import CommunicationLayer.debug
import GameState
import Player

abstract class Character {
    abstract val name: String
    open var lives: Int = 1
    open var hasDayFunction: Boolean = false

    abstract fun getFraction(real: Boolean = true): Fraction
    fun isAlive() = this.lives > 0

    open fun dayFunction(gameState: GameState) = debug("[$name nie ma funkcji dziennej. Omijamy]")
    open fun nightFunction(gameState: GameState) = debug("[$name nie ma funkcji nocnej. Omijamy]")
    open fun onlyFirstNightFunction(gameState: GameState) = debug("[$name nie ma funkcji nocy zerowej. Omijamy]")

    open fun killOnVotingTime(gameState: GameState, voters: List<Player>) =
        debug("[$name nie ma funkcji przy śmierci przy głosowaniu. Omijamy]").also { this.kill(gameState) }

    open fun kill(gameState: GameState) = debug("[$name nie ma funkcji przy śmierci. Omijamy]")
        .also { this.lives -= 1; }
        .also { if (lives < 0) debug("[Coś nie tak, życie == -1? $this // debugging xDDD]") }

    open fun killSilently(gameState: GameState) = debug("[$name nie ma funkcji przy cichej śmierci. Omijamy]")
        .also { this.lives = 0; }
}