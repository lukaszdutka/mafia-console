package characters.implementations

import GameState
import characters.MiastoCharacter

class KomisarzCattani : MiastoCharacter() {
    override val name: String = "Komisarz Cattani"

    override fun nightFunction(gameState: GameState) {
        println("Komisarz Cattani wybiera jedną osobę, której frakcję chce sprawdzić")

        val player = gameState.pickPlayers(1)[0]
        println("Gracz [${player.playerName}] ma funkcję z frakcji [${player.character.getFraction(real = true)}].")
    }
}