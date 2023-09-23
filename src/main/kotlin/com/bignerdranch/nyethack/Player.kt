package com.bignerdranch.nyethack

import kotlin.random.Random

class Player (
    initialName: String,
    val hometown: String = "Neversummer",
   override var healthPoints: Int,
    val isImmortal: Boolean
) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) {
            field = value.trim()
        }

    //    val hometown = hometown
//    var healthPoints = healthPoints
//    val isImmortal = isImmortal
    val title: String
        get() = when{
            name.all { it.isDigit() } -> "The Identifable"
            name.none{it.isLetter()} -> "The Witness Protection Member"
            name.count{it.lowercase() in "aeiou"} > 4 -> "The Master of Vowels"
            else -> "The Renowned Hero"
        }
    val prophecy by lazy {
        narrate("$name embarks on an aruous quest to locate a fortune teller")
        Thread.sleep(3000)
        narrate("The fortune teller bestows a prophecy upon $name")
        "An intrepid hero from $hometown shall some day "+ listOf(
            "form an unlikely bond between two warring factions",
            "take possession of an otherworldly blade",
            "bring the gift of creation back to the world",
            "best the world-eater"
        ).random()
    }
    override val diceCount= 3
    override val diceSides =4
    init {
        require(healthPoints > 0){"healthPoints must be greater than zero"}
        require(name.isNotBlank()){"Player must have a name"}
    }
    constructor(name: String) : this(
        initialName = name,
        healthPoints = 100,
        isImmortal = false
    ) {
        if (name.equals("Jason", ignoreCase = true)){
            healthPoints = 500
        }
    }
    fun castFireball(numFireballs: Int = 2) {
        narrate("A glass of Fireball spring into existence (x$numFireballs)")
    }
    fun changeName(newName: String){
        narrate("$name legally changes their name to $newName")
        name = newName
    }
    fun prophesize(){
        narrate("$name thinks about their future")
        narrate("A fortune teller told Madrigal, \"$prophecy\"")
    }
    override fun takeDamage(damage: Int) {
        if (!isImmortal){
            healthPoints -=damage
        }
    }
//    override fun attack(opponent: Fightable) {
//
//    }
}
//open class Weapon(val name: String, val type: String) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Weapon
//
//        if (name != other.name) return false
//        if (type != other.type) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = name.hashCode()
//        result = 31 * result + type.hashCode()
//        return result
//    }
//}
//367