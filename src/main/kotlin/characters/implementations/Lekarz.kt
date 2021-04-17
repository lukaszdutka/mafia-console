package characters.implementations

import GameState
import characters.MiastoCharacter

class Lekarz : MiastoCharacter() {
    override val name: String = "Lekarz"

    override fun nightFunction(gameState: GameState) {
        println("Lekarz wybiera jedną osobę, którą tej nocy chce uleczyć")

        val player = gameState.pickPlayers(1)[0]
        println("Lekarz będzie leczył: ${player.playerName}")
        gameState.healTargets.add(player)
    }
}