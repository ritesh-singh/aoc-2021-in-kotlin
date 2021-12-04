package day01

import readInput

fun main() {

    // T-C -> O(N)
    fun part1(input: List<String>): Int {
        if (input.size <= 1)
            return 0

        var prev = input[0].toInt()
        var count = 0
        for (i in 1 until input.size){
            if (input[i].toInt() > prev) count++
            prev = input[i].toInt()
        }
        return count
    }

    // T-C -> O(N)
    // S-C -> O(N)
    fun part2(input: List<String>): Int {
        if (input.size < 4)
            return 0

        val sumOfSlidingWindow = arrayListOf<Int>()
        var sum = input[0].toInt() + input[1].toInt() + input[2].toInt()
        sumOfSlidingWindow.add(sum)
        for (i in 1..input.size-3){
            sum = sum - input[i-1].toInt() + input[i+2].toInt()
            sumOfSlidingWindow.add(sum)
        }
        return part1(sumOfSlidingWindow.toList().map { it.toString() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day01/Day01_test")
    check(part1(testInput) == 7)

    check(part2(testInput) == 5)

    val input = readInput("/day01/Day01")
    println(part1(input))
    println(part2(input))
}
