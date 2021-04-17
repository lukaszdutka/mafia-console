package characters

abstract class MafiaCharacter : Character() {
    override fun getFraction(real: Boolean): Fraction = Fraction.MAFIA
}