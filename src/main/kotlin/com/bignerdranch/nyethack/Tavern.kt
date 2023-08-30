package com.bignerdranch.nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")
    .map{it.split(",")}

//private val menuItems = List(menuData.size) { index ->
//    val (_, name, _) = menuData[index].split(",")
//    name }
//private val menuItems = menuData.map { menuEntry ->
//    val (_, name, _)=menuEntry.split(",")
//    name
//}
private val menuItems = menuData.map{ (_, name, _) -> name}
//private val menuItemPrices: Map<String, Double> = List(menuData.size){
//    index -> val (_, name, price) = menuData[index].split(",")
//    private val menuItemPrices = menuData.associate { menuEntry ->
//    val (_, name, price) = menuEntry.split(",")
private val menuItemPrices = menuData.associate { (_, name, price) ->
    name to price.toDouble()
}

//private val menuItemTypes: Map<String, String> = List(menuData.size){
//    index -> val (type, name, _) = menuData[index].split(",")
//private val menuItemTypes = menuData.associate { menuEntry ->
//    val (type, name, _) = menuEntry.split(",")
    private val menuItemTypes = menuData.associate { (type, name, _) ->
        name to type
}


fun visitTavern() {
    narrate("${player.name} enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    narrate(menuItems.joinToString())




//    val patrons: MutableSet<String> = mutableSetOf()
    val patrons: MutableSet<String> = firstNames.shuffled()
        .zip(lastNames.shuffled()){ firstName, lastName -> "$firstName $lastName"}
        .toMutableSet()
    val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        player.name to 4.50,
        *patrons.map { it to 6.00 }.toTypedArray()
    )
//    printMenu()
//    while (patrons.size < 5) {
//       val patronName = "${firstNames.random()} ${lastNames.random()}"
//    patrons+=patronName
//    patrons.forEach{ patronName ->
//        patronGold += patronName to 6.0
//    }


//    println(patronGold["Madrigal"])
//    println(patronGold["Taernyl"])
//    println(patronGold["Eli"])
        narrate("${player.name} sees several patrons in the tavern:")
        narrate(patrons.joinToString())

    val itemOfDay = patrons.flatMap { getFavoriteMenuItems(it) }.random()
    narrate("The item of the day is the $itemOfDay")

        repeat(3) {
            placeOrder(patrons.random(), menuItems.random(), patronGold)
        }
    displayPatronBalances(patronGold)

//    val departingPatrons: List<String> = patrons
        patrons
        .filter { patron -> patronGold.getOrDefault(patron, 0.0) <4.0 }
//patrons-=departingPatrons
//    patronGold-=departingPatrons
            .also { departingPatrons ->
                patrons -= departingPatrons
                patronGold -= departingPatrons
            }
//    departingPatrons.forEach{patron ->
            .forEach{patron ->
    narrate("${player.name} sees $patron departing the tavern")
}
narrate("There are still some patrons in the tavern")
    narrate(patrons.joinToString())
//    menuData.forEachIndexed { index, data ->
//        println("$index : $data")
//    }
}
private fun getFavoriteMenuItems(patron: String): List<String>{
    return when(patron){
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("dessert") == true
        }
        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}

private fun placeOrder(patronName: String,
                       menuItemName: String,
patronGold: MutableMap<String, Double>) {
    val itemPrice = menuItemPrices.getValue(menuItemName)
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        val action = when (menuItemTypes[menuItemName]){
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else{ narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }

}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("${player.name} intuitively knows how much money each patron has")
    patronGold.forEach{ (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}


//fun printMenu()
//{
//    println()
//    val hello = "*** Welcome to $TAVERN_NAME ***"
//    val cnt = hello.count()
//    println(hello)
//    println()
//
//    menuData.forEach { t ->
//        val (type, name, price) = t.split(',')
//        val nameOut = name[0].toUpperCase() + name.substring(1, name.count())
//        val pos = price.indexOf('.')
//        val priceOut = if (price.count() - pos == 2) {
//            price + '0'
//        } else {
//            price
//        }
//        val s = nameOut.padEnd(cnt - priceOut.count(), '.')
//        println(s + priceOut)
//    }
//    println()
//}

//задание 231, 253, 254
//265
//Функции области видимости