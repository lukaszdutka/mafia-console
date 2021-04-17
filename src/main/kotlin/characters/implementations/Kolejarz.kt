package characters.implementations

import GameState
import characters.SyndykatCharacter

class Kolejarz : SyndykatCharacter() {
    override val name: String = "Kolejarz"
    override var hasDayFunction: Boolean = true

    override fun dayFunction(gameState: GameState) {
        val pickedPlayers = gameState.pickPlayers(2)

        println("Kolejarz wywiózł siebie i następujące postaci:")

        pickedPlayers.forEach {
            println(it.playerName)
            it.character.killSilently(gameState)
        }
        this.killSilently(gameState)
        this.hasDayFunction = false
    }
}