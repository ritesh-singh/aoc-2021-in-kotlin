package day07

import readInput
import kotlin.math.abs

fun main() {

    fun Int.gaussSum() = (this * (this + 1)) / 2

    fun part1(inputs: List<String>): Int {
        val crabHPos = inputs[0].split(",").map { it.toInt() }.sorted()
        var fuelCost = Integer.MAX_VALUE
        (crabHPos[0]..crabHPos[crabHPos.size-1]).forEach { pos ->
            val fCost = crabHPos.map { abs(pos - it) }.reduce { acc, element -> acc + element }
            if (fCost < fuelCost) fuelCost = fCost
        }
        return fuelCost
    }


    fun part2(inputs: List<String>): Int {
        val crabHPos = inputs[0].split(",").map { it.toInt() }.sorted()
        var fuelCost = Integer.MAX_VALUE
        (crabHPos[0]..crabHPos[crabHPos.size-1]).forEach { pos ->
            val fCost = crabHPos.map { abs(pos - it).gaussSum() }.reduce { acc, element -> acc + element }
            if (fCost < fuelCost) fuelCost = fCost
        }
        return fuelCost
    }

    val testInput = readInput("/day07/Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("/day07/Day07")
    check(part1(input) == 342534)
    check(part2(input) == 94004208)
    println(part1(input))
    println(part2(input))
}
