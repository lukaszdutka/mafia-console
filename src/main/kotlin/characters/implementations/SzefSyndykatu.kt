package characters.implementations

import GameState
import characters.Fraction
import characters.SyndykatCharacter

class SzefSyndykatu : SyndykatCharacter() {
    override val name = "Szef Syndykatu"

    override fun onlyFirstNightFunction(gameState: GameState) {
        println("Budzi się $name")
        println("Szef Syndykatu poznaje, że w jego frakcji są następujące osoby:")
        gameState.alivePlayers().forEachIndexed { i, player ->
            if (player.character.getFraction(real = true) == Fraction.SYNDYKAT) {
                println("$i. ${player.playerName} jest z ${Fraction.SYNDYKAT} i jego funkcja to [${player.character.name}]")
            }
        }
    }
}