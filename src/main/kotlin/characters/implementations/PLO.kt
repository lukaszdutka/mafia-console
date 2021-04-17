package characters.implementations

import GameState
import characters.MiastoCharacter

class PLO : MiastoCharacter() {
    override val name = "PLO"

    override fun onlyFirstNightFunction(gameState: GameState) {
        println("PLO:")
        val player = gameState.pickPlayers(1)[0]
        println("PLO poznaje, że [${player.playerName}] to [${player.character.name},${player.character.getFraction(real = true)}]")
    }
}