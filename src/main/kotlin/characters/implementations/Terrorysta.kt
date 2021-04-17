package characters.implementations

//import GameState
//import characters.MafiaCharacter
//
//class Terrorysta : MafiaCharacter() {
//    override val name = "Terrorysta"
//
//    override fun kill(gameState: GameState) {
//        println("Ginie terrorysta")
//        val terrorist = gameState.players.find { it.character is Terrorysta }!!
//        val leftPlayer = gameState.getLeftAlivePlayerTo(terrorist)
//        val rightPlayer = gameState.getRightAlivePlayerTo(terrorist)
//
//        // TODO: 17/04/2021 kurde co z terrorystą jak zabije flipa/flapa? Albo święty go zabije etc.? xD powalone
//        println("A razem z nim traci życie:")
//        println(leftPlayer.playerName)
//        leftPlayer.character.kill(gameState)
//        println("oraz ${rightPlayer.playerName}")
//        rightPlayer.character.kill(gameState)
//    }
//}