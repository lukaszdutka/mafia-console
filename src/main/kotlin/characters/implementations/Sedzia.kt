package characters.implementations

import Player
import characters.MiastoCharacter

class Sedzia : MiastoCharacter() {
    override val name = "Sędzia"

    fun agreeOnDuelOrNot(challenging: Player, challenged: Player): Boolean {
        println("Czy sędzia zgadza się na pojedynek między wyzywającym: ${challenging.playerName} i wyzywanym: ${challenged.playerName}? (t)ak albo (n)ie.")
        return when (readLine()!![0].toLowerCase()) {
            't' -> true
            'n' -> false
            else -> agreeOnDuelOrNot(challenging, challenged)
        }
    }
}
