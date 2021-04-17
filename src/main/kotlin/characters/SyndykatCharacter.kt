package characters

abstract class SyndykatCharacter : Character() {
    override fun getFraction(real: Boolean): Fraction = Fraction.SYNDYKAT
}