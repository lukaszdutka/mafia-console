import CommunicationLayer.bigMessage
import CommunicationLayer.debug
import CommunicationLayer.mediumMessage
import CommunicationLayer.messageToSay
import CommunicationLayer.smallMessage
import characters.Fraction.*
import characters.implementations.Sedzia
import characters.implementations.SzybkiZMafii
import characters.implementations.SzybkiZMiasta

class Game(private val gameState: GameState) {

    fun startGame() {
        gameState.validateGame()
        zeroNight()

        while (!isGameFinished()) {
            day()
            if (!isGameFinished()) {
                night()
                gameState.resolveNight()
            }
        }
        gameFinished()
    }

    private fun day() {
        bigMessage("Dzień!")
        mediumMessage(gameState.getStan())
        var shouldStopDay = false
        while (!shouldStopDay) {
            shouldStopDay = printPossibilitiesAtDay()
        }
        mediumMessage("Koniec dnia")
    }

    private fun printPossibilitiesAtDay(): Boolean {
        println("Opcje dla Mistrza Gry, wybierz jedną:")
        println("0. Pojedynek")
        println("1. Głosowanie na koniec dnia [powoduje koniec dnia]")
        val playersWithDayFunction = gameState.alivePlayers()
            .filter { it.character.hasDayFunction }
        playersWithDayFunction
            .forEachIndexed { index, player -> println("${index + 2}. Funkcja [${player.character.name}] - gracza [${player.playerName}]") }

        val choice: Int = readLine()!!.toInt()
        when (choice) {
            0 -> duel()
            1 -> {
                voteAtTheEndOfTheDay()
                return true
            }
            else -> if (playersWithDayFunction.isNotEmpty()) {
                playersWithDayFunction[choice - 2].character.dayFunction(gameState)
                if (isGameFinished()) {
                    return true
                }
            }
        }
        return false
    }

    private fun voteAtTheEndOfTheDay() {
        mediumMessage("Głosowanie na koniec dnia")
        messageToSay("Pierwsza propozycja?")
        val firstOption = gameState.pickPlayers(1)[0]
        val firstKillOrCheck = gameState.killOrCheck()
        messageToSay("Pierwsza propozycja: ${firstOption.playerName}, $firstKillOrCheck")
        messageToSay("Kontrpropozycja:")
        val secondOption = gameState.pickPlayers(1)[0]
        val secondKillOrCheck = gameState.killOrCheck()
        messageToSay("Druga propozycja: ${secondOption.playerName}, $secondKillOrCheck")

        messageToSay("Głosowanie, nie można się wstrzymywać!")
        messageToSay("kto jest za $firstKillOrCheck, ${firstOption.playerName}?")
        val firstOptionVoters = gameState.pickAnyPlayers()

        messageToSay("kto jest za $secondKillOrCheck, ${secondOption.playerName}?")
        val secondOptionVoters = gameState.pickAnyPlayers(butWithout = firstOptionVoters)

        if (firstOptionVoters.size > secondOptionVoters.size) {
            when (firstKillOrCheck) {
                VotingType.SPRAWDZENIE -> personChecked(firstOption)
                VotingType.ZABICIE -> firstOption.character.killOnVotingTime(gameState, firstOptionVoters)
            }
        }
        if (firstOptionVoters.size < secondOptionVoters.size) {
            when (secondKillOrCheck) {
                VotingType.SPRAWDZENIE -> personChecked(secondOption)
                VotingType.ZABICIE -> secondOption.character.killOnVotingTime(gameState, secondOptionVoters)
            }
        }
        if (firstOptionVoters.size == secondOptionVoters.size) {
            // TODO: 17/04/2021 "Co na remis?"
        }
        smallMessage("Koniec głosowania")
    }

    private fun personChecked(player: Player) {
        messageToSay("Dowiadujecie się, że ${player.playerName} jest z ${player.character.getFraction(real = false)}")
    }

    private fun duel() {
        if (gameState.wasDuelPerformedThisDay) {
            println("Dzisiaj już był pojedynek!")
            return
        }
        //kto wyzywa kogo xD
        println("Wybierz dwie liczby, najpierw kto wyzywa a potem kogo")
        val players = gameState.pickPlayers(2)

        val challengingPlayer = players[0]
        val challengedPlayer = players[1]

        val player = gameState.alivePlayers().firstOrNull { it.character is Sedzia }
        val judge = player?.character
        val agreement = if (judge is Sedzia && judge.isAlive()) {
            judge.agreeOnDuelOrNot(challengingPlayer, challengedPlayer)
        } else {
            debug("Sędzia nie żyje, więc pojedynek się odbędzie.")
            true
        }

        if (agreement) {
            messageToSay("Jest zgoda na pojedynek, czas na walkę! [Rozmawiajcie!]")
            println("Teraz czas na głosowanie:")
            println("Kto jest za zabiciem ${challengingPlayer.playerName}?")
            val challengingVoters = gameState.pickAnyPlayers()

            println("Kto jest za zabiciem ${challengedPlayer.playerName}?")
            val challengedVoters = gameState.pickAnyPlayers(challengingVoters)

            val isChallengingSzybki =
                challengingPlayer.character is SzybkiZMafii || challengingPlayer.character is SzybkiZMiasta
            val isChallengedSzybki =
                challengedPlayer.character is SzybkiZMafii || challengedPlayer.character is SzybkiZMiasta

            if (isChallengingSzybki && isChallengedSzybki || !isChallengedSzybki && !isChallengingSzybki) { //two szybki or no szybcy
                when {
                    challengingVoters.size > challengedVoters.size -> {
                        killInDuel(challengingPlayer, challengingVoters)
                    }
                    challengingVoters.size < challengedVoters.size -> {
                        killInDuel(challengedPlayer, challengedVoters)
                    }
                    else -> {
                        killInDuel(challengingPlayer, challengingVoters)
                        killInDuel(challengedPlayer, challengedVoters)
                    }
                }
            } else { //szybki vs not szybki
                if (isChallengedSzybki && !isChallengingSzybki) {
                    killInDuel(challengingPlayer, challengingVoters)
                }
                if (!isChallengedSzybki && isChallengingSzybki) {
                    killInDuel(challengedPlayer, challengedVoters)
                }
            }

            gameState.wasDuelPerformedThisDay = true
            println("Pojedynek odbyty!")
        } else {
            messageToSay("Nie ma zgody na pojedynek")
        }
    }

    private fun killInDuel(player: Player, voters: List<Player>) {
        messageToSay("W pojedynku ginie ${player.playerName}")
        player.character.killOnVotingTime(gameState, voters)
    }

    private fun night() {
        bigMessage("Noc!")
        mafiaShot()
        gameState.alivePlayers().forEach { it.character.nightFunction(gameState) }
    }

    private fun mafiaShot() {
        mediumMessage("Budzi się mafia i wybiera osobę do zastrzelenia:")
        val target = gameState.pickPlayers(1)[0]
        smallMessage("${target.playerName} jest celem mafii.")
        gameState.mafiaShotTargets.add(target)
    }

    private fun zeroNight() {
        bigMessage("Noc zerowa")
        gameState.alivePlayers().forEach {
            it.character.onlyFirstNightFunction(gameState)
        }
        //todo zapoznanie mafii ze sobą
    }

    private fun isGameFinished(): Boolean {
        val fractionToCount = gameState.getFractionToCount()
        return fractionToCount[MIASTO] == 0 && fractionToCount[SYNDYKAT] == 0 && fractionToCount[MAFIA] != 0
                || fractionToCount[MIASTO] == 0 && fractionToCount[SYNDYKAT] != 0 && fractionToCount[MAFIA] == 0
                || fractionToCount[MIASTO] != 0 && fractionToCount[SYNDYKAT] == 0 && fractionToCount[MAFIA] == 0
    }

    private fun gameFinished() =
        gameState.getFractionToCount().forEach { if (it.value != 0) bigMessage("Gratulacje, ${it.key} wygrywa.") }
}
