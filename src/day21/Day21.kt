package day21

import readInput

class Player(val dice: DeterministicDice, startSpace: Int, private val winScore:Int = 1000) {
    var score: Int = 0
        private set
    private var onSpaceIndex = startSpace - 1
    fun won() = score >= winScore

    private val circularTrack = IntArray(10) { 1 + it }

    fun play() {
        var toMove = 0
        repeat(3) {
            toMove += dice.rollDice()
        }
        onSpaceIndex = (toMove + circularTrack[onSpaceIndex] - 1) % circularTrack.size
        score += circularTrack[onSpaceIndex]
    }

    fun result() = score * dice.dieRolled
}

class DeterministicDice {
    private val noOfSides = 100
    private var dicePos = 0
    var dieRolled = 0
        private set

    fun rollDice(): Int {
        if (dicePos == noOfSides) dicePos = 0
        dieRolled++
        return ++dicePos
    }
}

class Game(startPosP1: Int, startPosP2: Int) {
    private val dice = DeterministicDice()
    private val player1 = Player(dice = dice, startSpace = startPosP1)
    private val player2 = Player(dice = dice, startSpace = startPosP2)

    fun start(): Int {
        while (true) {
            player1.play()
            if (player1.won()) return player2.result()
            player2.play()
            if (player2.won()) return player1.result()
        }
    }
}

fun main() {
    fun part1(inputs: List<String>): Int {
        val game = Game(
            startPosP1 = inputs[0].split(":")[1].trim().toInt(),
            startPosP2 = inputs[1].split(":")[1].trim().toInt(),
        )
        return game.start()
    }

    val testInput = readInput("/day21/Day21_test")
    check(part1(testInput) == 739785)


    val input = readInput("/day21/Day21")
    println(part1(input))
}
