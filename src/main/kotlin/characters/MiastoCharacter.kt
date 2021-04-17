package characters

abstract class MiastoCharacter : Character() {
    override fun getFraction(real: Boolean): Fraction = Fraction.MIASTO
}