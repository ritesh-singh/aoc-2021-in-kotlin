package day10

import readInput
import java.util.*

fun main() {

    val characterMap = hashMapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    val illegalCharacterScoreMap = hashMapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val closingCharacterScoreMap = hashMapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun List<String>.getTotalSyntaxErrorAndMiddleScore(): Pair<Int, Long> {
        val listOfScores = mutableListOf<Long>()
        var totalSyntaxError = 0
        for (input in this){
            val stack = Stack<Char>()
            var isCorruptedLine = false
            for (char in input) {
                when (char) {
                    '(', '[', '{', '<' -> stack.push(char)
                    ')',']','}','>' -> {
                        val topElement = stack.peek()
                        val matchingOfTopElement = characterMap[topElement]
                        if (matchingOfTopElement == char) {
                            stack.pop()
                        }else{
                            totalSyntaxError += illegalCharacterScoreMap[char]!!
                            isCorruptedLine = true
                            break
                        }
                    }
                }
            }
            if (!isCorruptedLine){
                var totalScore = 0L
                while (stack.isNotEmpty()){
                    val element = stack.pop()
                    val matching = characterMap[element]
                    totalScore = (totalScore * 5) + closingCharacterScoreMap[matching]!!
                }
                listOfScores.add(totalScore)
            }
        }

        listOfScores.sort()

        return Pair(totalSyntaxError,listOfScores[listOfScores.size/2])
    }

    fun part1(inputs: List<String>): Int  = inputs.getTotalSyntaxErrorAndMiddleScore().first

    fun part2(inputs: List<String>): Long  = inputs.getTotalSyntaxErrorAndMiddleScore().second

    val testInput = readInput("/day10/Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("/day10/Day10")
    println(part1(input))
    check(part1(input) == 392043)
    println(part2(input))
    check(part2(input) == 1605968119L)
}
