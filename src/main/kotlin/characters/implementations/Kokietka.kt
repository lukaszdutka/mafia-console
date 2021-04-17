package characters.implementations

import characters.Fraction
import characters.MafiaCharacter

class Kokietka : MafiaCharacter() {
    override val name: String = "Kokietka"

    override fun getFraction(real: Boolean): Fraction = if (real) Fraction.MAFIA else Fraction.MIASTO
}