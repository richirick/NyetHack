package com.bignerdranch.nyethack

//val player = Player("Jason")
// val room = Room(name = String())
lateinit var player: Player
fun main() {
//    narrate("${player.name} is ${player.title}")
//    player.changeName("Aurelia")
    /* narrate("A hero enters the town of Kronstadt. What is their name?")
     // Выводит сообщение желтым цветом
     "\u001b[33;1m$message\u001b[0m"
 }
 { message ->
     val heroName = readLine()
     require(heroName != null && heroName.isNotEmpty()) {
         "The hero must have a name."
     }*/
//    if (::player.isInitialized){
//        narrate("Welcome to NeytHack, ${player.name}!")
//    }
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
    var currentRoom: Room = TownSquare()
    private var currentPosition = Coordinate(0, 0)
    init {
        narrate("Wekcome, adventurer")
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
    private class GameInput(arg: String?){
        private val input = arg?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1){ "" }
    fun processCommand()= when(command.lowercase()){
        else-> narrate("I'm not sure what you're trying to do")
    }}
}
//private fun createTitle(name: String): String {
//    return when {
//        name.all { it.isDigit() } -> "The Identifiable"
//        name.none { it.isLetter() } -> "The Witness Protection Member"
//        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowels"
//        else -> "The Renowned Hero"
//    } }
//создать игру-аркаду с котиками по мотивам чужого
//с 347