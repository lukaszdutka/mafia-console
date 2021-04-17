package characters.implementations

import GameState
import Player
import characters.Character
import characters.MiastoCharacter

class Swiety : MiastoCharacter() {
    override val name = "Święty"

    override fun killOnVotingTime(gameState: GameState, voters: List<Player>) {
        this.kill(gameState)
//        var terroristVoted: Character? = null
        voters.forEach {
            when (it.character) {
//                is Terrorysta -> terroristVoted = it.character
                else -> it.character.kill(gameState)
            }
        }
//        terroristVoted?.kill(gameState)
    }
}