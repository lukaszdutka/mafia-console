import characters.Character
import characters.CharacterFactory

fun main() {
    CommunicationLayer.setup(shouldShowDebugMessages = true)

    CommunicationLayer.bigMessage("Witaj w mafii! Skonfiguruj grę.")

    val players = createPlayers()

    CommunicationLayer.mediumMessage("Skład gry")
    players.forEachIndexed { i, player -> println("$i. ${player.playerName} [${player.character.name}]") }

    val gameState = GameState(players)
    val game = Game(gameState)

    game.startGame()
}

private fun createPlayers(): List<Player> {
    val playerCount: Int = loadPlayerCount()
    val playerNames: List<String> = loadPlayerNames(playerCount)
    val characters: List<Character> = loadCharacters(playerCount)

    return playerNames.zip(characters)
        .map { Player(it.second, it.first) }
}

fun loadPlayerCount(): Int {
    print("Podaj liczbę graczy: ")
    return readLine()!!.toInt()
}

fun loadPlayerNames(playerCount: Int): List<String> =
    IntRange(1, playerCount)
        .map { i ->
            print("Podaj imię nr $i: ")
            readLine()!!
        }

fun loadCharacters(playerCount: Int): List<Character> {
    println("Jakie chcesz postaci? Podaj $playerCount indexów, oddzielonych przecinkami np. dla 4 graczy: '1,3,4,5'")
    val allCharacters = CharacterFactory.allCharacters
    allCharacters.forEachIndexed { i, character -> println("$i. ${character.name}, [${character.getFraction(real = true)}]") }

    val numbersAsString = readLine()!!.split(",")
    if (numbersAsString.size != playerCount) {
        throw RuntimeException("Wrong number of players picked! `$playerCount` required, was: `$numbersAsString`")
    }
    return numbersAsString
        .map { it.toInt() }
        .map { allCharacters[it] }
        .shuffled()
}
