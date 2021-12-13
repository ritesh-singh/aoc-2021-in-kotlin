package day13

import readInput

fun main() {

    data class Point(val x: Int, val y: Int)
    data class Instruction(val axis: String, val value: Int)

    fun Array<CharArray>.print(rowSize: Int, colSize: Int) {
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                print(this[row][col])
                print(" ")
            }
            println()
        }
        println()
    }

    fun solution(inputs: List<String>, onlyFirstFold: Boolean): Int {
        val coords = mutableListOf<Point>()
        val instructions = mutableListOf<Instruction>()
        var xAxisSize = Int.MIN_VALUE
        var yAxisSize = Int.MIN_VALUE
        for (input in inputs) {
            if (input.isBlank()) continue
            if (input.startsWith("fold")) {
                val instruction = input.split(" ")[2].trim().split("=")
                    .let { Instruction(axis = it[0].trim(), value = it[1].trim().toInt()) }
                instructions.add(instruction)
            } else {
                val point = input.trim().split(",").let {
                    val point = Point(x = it[1].trim().toInt(), y = it[0].toInt())
                    if (point.x > yAxisSize) yAxisSize = point.x
                    if (point.y > xAxisSize) xAxisSize = point.y
                    point
                }
                coords.add(point)
            }
        }
        ++xAxisSize
        ++yAxisSize

        val matrix = Array(yAxisSize) { CharArray(xAxisSize) { '.' } }
        coords.forEach { matrix[it.x][it.y] = '#' }

        var totalDots = coords.size

        val noOfTimes = if (onlyFirstFold) 1 else instructions.size

        repeat(noOfTimes) { counter ->
            instructions[counter].let {
                if (it.axis == "y") {
                    // fold bottom up
                    var rowToMergeWith = it.value
                    for (yAxisBelowFoldingPoint in it.value + 1 until yAxisSize) {
                        --rowToMergeWith
                        for (col in 0 until xAxisSize) {
                            if (matrix[yAxisBelowFoldingPoint][col] == '#') --totalDots
                            if ((matrix[yAxisBelowFoldingPoint][col] == '.' && matrix[rowToMergeWith][col] == '#')
                                ||
                                (matrix[yAxisBelowFoldingPoint][col] == '#' && matrix[rowToMergeWith][col] == '.')
                            ) {
                                if (matrix[yAxisBelowFoldingPoint][col] == '#' && matrix[rowToMergeWith][col] == '.') {
                                    ++totalDots
                                }
                                matrix[rowToMergeWith][col] = '#'
                            }
                        }
                    }

                    yAxisSize = it.value
                } else {
                    var colToMergeWith = it.value
                    for (xAxisAfterFoldingPoint in it.value + 1 until xAxisSize) {
                        --colToMergeWith
                        for (row in 0 until yAxisSize) {
                            if (matrix[row][xAxisAfterFoldingPoint] == '#') --totalDots
                            if ((matrix[row][xAxisAfterFoldingPoint] == '.' && matrix[row][colToMergeWith] == '#')
                                ||
                                (matrix[row][xAxisAfterFoldingPoint] == '#' && matrix[row][colToMergeWith] == '.')
                            ) {
                                if (matrix[row][xAxisAfterFoldingPoint] == '#' && matrix[row][colToMergeWith] == '.') {
                                    ++totalDots
                                }
                                matrix[row][colToMergeWith] = '#'
                            }
                        }
                    }

                    xAxisSize = it.value
                }
            }
        }

        if (!onlyFirstFold) matrix.print(yAxisSize,xAxisSize)

        return totalDots
    }


    val testInput = readInput("/day13/Day13_test")
    check(solution(testInput,onlyFirstFold = true) == 17)
    solution(testInput, onlyFirstFold = false)

    val input = readInput("/day13/Day13")
    println(solution(input,onlyFirstFold = true))
    solution(input,onlyFirstFold = false)
}
