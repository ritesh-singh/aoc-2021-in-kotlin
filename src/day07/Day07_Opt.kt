package day07

import readInput
import kotlin.math.abs
import kotlin.math.roundToInt


fun main() {

    fun List<Int>.median() = this[this.size / 2]

    fun List<Int>.mean() = this.sum() / this.size

    fun Int.gaussSum() = (this * (this + 1)) / 2

    fun part1(inputs: List<String>): Int {
        return inputs[0].split(",").map { it.toInt() }.sorted()
            .let { sortedList ->
                val median = sortedList.median()
                sortedList.sumOf { abs(it - median) }
            }
    }

    fun part2(inputs: List<String>): Int {
        val list = inputs[0].split(",").map { it.toInt() }
        val avg: Int = list.average().toInt()
        var cost = 0
        for (crabPos in list) {
            val diff = abs(crabPos - avg)
            cost += diff.gaussSum()
        }

        return cost
    }

    val testInput = readInput("/day07/Day07_test")
    println("part1 - test -> ${part1(testInput)}")
    check(part1(testInput) == 37)
    check(part2(testInput) == 170)

    val input = readInput("/day07/Day07")
    check(part1(input) == 342534)
    check(part2(input) == 94004208)
    println(part1(input))
    println(part2(input))
}


