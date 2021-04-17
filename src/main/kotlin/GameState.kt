import characters.Fraction

class GameState(private val players: List<Player>) {

    var wasDuelPerformedThisDay: Boolean = false
    val healTargets: MutableList<Player> = mutableListOf()
    val mafiaShotTargets: MutableList<Player> = mutableListOf()

    fun resolveNight() {
        mafiaShotTargets.forEach {
            if (!healTargets.contains(it)) {
                println("${it.playerName} ginie w trakcie nocy.")
                it.character.kill(this)
            }
        }
        resetStateAfterNight()
    }

    private fun resetStateAfterNight() {
        wasDuelPerformedThisDay = false
        healTargets.clear()
        mafiaShotTargets.clear()
    }

    fun pickPlayers(numberOfPlayersToPick: Int): List<Player> {
        println("Podaj $numberOfPlayersToPick numerów oddzielonych przecinkami.")
        printPlayerNamesWithIndexes()
        val numbersAsString = readLine()!!.split(",")
        if (numbersAsString.size != numberOfPlayersToPick) {
            throw RuntimeException("Wrong number of players picked! `$numberOfPlayersToPick` required, was: `$numbersAsString`")
        }
        return numbersAsString
            .map { it.toInt() }
            .map { alivePlayers()[it] }

    }

    fun pickAnyPlayers(butWithout: List<Player> = emptyList()): List<Player> {
        println("Podaj wszystkie numery oddzielone przecinkami.")
        printPlayerNamesWithIndexes(butWithout)
        val numbersAsString = readLine()!!.split(",")
        if (numbersAsString.isEmpty()) return emptyList()
        return numbersAsString
            .map { it.toInt() }
            .map { alivePlayers()[it] }

    }

    private fun printPlayerNamesWithIndexes(butWithout: List<Player> = emptyList()) =
        alivePlayers().forEachIndexed { index, player ->
            if (!butWithout.contains(player)) {
                println("$index. ${player.playerName} [${player.character.name}]")
            }
        }

    fun getLeftAlivePlayerTo(player: Player): Player {
        val alivePlayers = alivePlayers()
        val playerIndex = alivePlayers.indexOf(player)

        val leftPlayerIndex = if (playerIndex == 0) alivePlayers.size - 1 else playerIndex - 1
        return alivePlayers[leftPlayerIndex]
    }

    fun getRightAlivePlayerTo(player: Player): Player {
        val alivePlayers = alivePlayers()
        val playerIndex = alivePlayers.indexOf(player)

        val rightPlayerIndex = if (playerIndex == alivePlayers.size - 1) 0 else playerIndex + 1
        return alivePlayers[rightPlayerIndex]
    }

    fun alivePlayers(): MutableList<Player> = players.filter { it.character.isAlive() }.toMutableList()

    fun getStan(): String {
        val fractionToCount = getFractionToCount()
        val miasto = fractionToCount[Fraction.MIASTO]
        val syndykat = fractionToCount[Fraction.SYNDYKAT]
        val mafia = fractionToCount[Fraction.MAFIA]

        return "Stan: miasto=$miasto, syndykat=$syndykat, mafia=$mafia"
    }

    fun getFractionToCount(): Map<Fraction, Int> =
        Fraction.values().associate { it to countFraction(fraction = it) }.toMap()

    private fun countFraction(fraction: Fraction): Int = alivePlayers()
        .filter { it.character.getFraction(real = true) == fraction }
        .size

    fun validateGame() {
        val state = getFractionToCount()
        if (state[Fraction.MIASTO] == 0 || state[Fraction.MAFIA] == 0) {
            throw RuntimeException("Brakuje członków miasta[${state[Fraction.MIASTO]}] lub mafii[${state[Fraction.MAFIA]}]!")
        }
    }

    fun killOrCheck(): VotingType {
        println("Sprawdzenie (s) czy zabicie (z)?")
        return when (readLine()!![0]) {
            's' -> VotingType.SPRAWDZENIE
            'z' -> VotingType.ZABICIE
            else -> killOrCheck()
        }
    }
}