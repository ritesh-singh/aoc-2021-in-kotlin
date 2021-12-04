package day02

import readInput

fun main() {

    // T-C -> O(N)
    fun part1(input: List<String>): Int {
        if (input.isEmpty()) return 0
        var horizontal = 0
        var depth = 0
        for (value in input){
            val command = value.split(" ")
            when(command[0]){
                "forward" -> horizontal += command[1].toInt()
                "down" -> depth += command[1].toInt()
                "up" -> depth -= command[1].toInt()
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        if (input.isEmpty()) return 0
        var horizontal = 0
        var depth = 0
        var aim = 0
        for (value in input){
            val command = value.split(" ")
            when(command[0]){
                "down" -> aim += command[1].toInt()
                "up" -> aim -= command[1].toInt()
                "forward" -> {
                    horizontal += command[1].toInt()
                    depth += aim * command[1].toInt()
                }
            }
        }
        return horizontal * depth
    }

    val testInput = readInput("/day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("/day02/Day02")
    println(part1(input))
    println(part2(input))
}
