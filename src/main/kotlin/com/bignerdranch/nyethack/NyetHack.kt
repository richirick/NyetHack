package com.bignerdranch.nyethack

import kotlin.system.exitProcess

//val player = Player("Jason")
// val room = Room(name = String())
lateinit var player: Player
fun main() {

    narrate("Welcome to NeytHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    // changeNarratorMood()
    Game.play()
}
private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?") { message ->
        // Выводит message желтым цветом
        "\u001b[33;1m$message\u001b[0m"
    }
    /*val input = readLine()
    require(input != null && input.isNotEmpty()) {
        "The hero must have a name."
    }
    return input*/
    println("Madrigal")
    return "Madrigal"
}
object Game{
    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(MonsterRoom("A Long Corridor"), Room("A Generic Room")),
        listOf(MonsterRoom("The Dungeon"))
    )
    var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)
    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")

    }
    fun play(){
        while (true){
            narrate("${player.name}, of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()
            print("> Enter your command: ")
//            println("Last command: ${readln()}")
            GameInput(readln()).processCommand()
        }
    }
    fun move(direction: Direction){
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)

        if (newRoom !=null){
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate("You cannot move ${direction.name}")
        }
    }
    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null) {
            narrate("There's nothing to fight here")
            return
        }
        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster)
            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }
        if (player.healthPoints <= 0) {
            narrate("You have been defeated! Thanks for playing")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated")
            monsterRoom.monster = null
        }
    }

    private class GameInput(arg: String?){
        private val input = arg?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1){ "" }
    fun processCommand()= when(command.lowercase()){
//        "prophesize" -> prophesizing(command)
//        "cast" ->castFireball(command)
//        "exit" -> endGame(command)
        "fight" -> fight()
        "move" ->{
            val direction = Direction.values()
                .firstOrNull{it.name.equals(argument, ignoreCase = true)}
            if (direction != null){
                move(direction)
            } else {
                narrate("I don't know what direction that is")
            }
        }
        else-> narrate("I'm not sure what you're trying to do")
    }
    }

//    private fun endGame(command: String) {
//        val end1 = Game
//        val end = readln()
//        if (end in "end") {
//            println("Game is end")
//        }
//    }
//
//    private fun castFireball(command: String){
//    val cast = player.castFireball()
//    val castF = readln()
//    if (castF in "cast"){
//        println(cast)
//    }
//}
//    private fun prophesizing(command: String) {
//        val prophesize2 = player.prophesize()
//        val prophesize1: String = readln()
//        if (prophesize1 in "prophesize"){
//            println(prophesize2)
//        }

}
//создать игру-аркаду с котиками по мотивам чужого
//с 356