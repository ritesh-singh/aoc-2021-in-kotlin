package day23

import readInput

fun main() {
    fun part1(inputs: List<String>): Int {
        println(inputs)
        return inputs.size
    }

    val testInput = readInput("/day23/Day23_test")
    part1(testInput)
}