package day15

import readInput
import java.util.*
import kotlin.Comparator
import kotlin.math.abs

private enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

private val isLocationValid: (row: Int, col: Int, rowSize: Int, colSize: Int) -> Boolean =
    { row, col, rowSize, colSize ->
        (row in 0 until rowSize) && (col in 0 until colSize)
    }

private val getLocationBasedOnDirection: (row: Int, col: Int, direction: day15.Direction) -> Pair<Int, Int> =
    { row, col, direction ->
        val pair = when (direction) {
            Direction.UP -> Pair(row - 1, col)
            Direction.DOWN -> Pair(row + 1, col)
            Direction.LEFT -> Pair(row, col - 1)
            Direction.RIGHT -> Pair(row, col + 1)
        }
        pair
    }


fun Array<IntArray>.getAdjacent(row: Int, col: Int): List<Pair<Int, Int>> {
    val adjacentList = mutableListOf<Pair<Int, Int>>()
    val rowSize = this.size
    val colSize = this[0].size
    for (direction in Direction.values()) {
        val adjacent = getLocationBasedOnDirection(row, col, direction)
        if (isLocationValid(adjacent.first, adjacent.second, rowSize, colSize)) {
            adjacentList.add(Pair(adjacent.first, adjacent.second))
        }
    }

    return adjacentList
}

private fun List<String>.initMatrix(matrix: Array<IntArray>) {
    val rowSize = size
    val colSize = this[0].length
    for (row in 0 until rowSize) {
        for (col in 0 until colSize) {
            matrix[row][col] = this[row][col].digitToInt()
        }
    }
}

private fun Array<IntArray>.printMatrix(){
    for (row in 0 until this.size) {
        for (col in 0 until this[0].size) {
            print(this[row][col])
            print(" ")
        }
        println()
    }
    println()
}

private data class VertexDistancePair(
    val location: Pair<Int,Int>,
    val riskLevel: Int
)

private class VertexDistancePairComparator : Comparator<VertexDistancePair> {
    override fun compare(first: VertexDistancePair, second: VertexDistancePair): Int {
        return first.riskLevel.compareTo(second.riskLevel)
    }
}

private fun dijkstra(start: Pair<Int, Int>, matrix: Array<IntArray>): Int {
    val priorityQueue = PriorityQueue(VertexDistancePairComparator())
    val visited = hashSetOf<Pair<Int,Int>>()
    val totalRiskLevel = hashMapOf<Pair<Int, Int>, Int>()

    totalRiskLevel[start] = 0
    priorityQueue.add(VertexDistancePair(start, 0))

    while (priorityQueue.isNotEmpty()){
        val (location, riskLevel) = priorityQueue.remove()
        visited.add(location)

        if (totalRiskLevel.getOrDefault(location, Int.MAX_VALUE) < riskLevel) continue

        for(adj in matrix.getAdjacent(location.first,location.second)){
            if (visited.contains(adj)) continue
            val newRiskLevel = totalRiskLevel.getOrDefault(location, Int.MAX_VALUE) + matrix[adj.first][adj.second]
            if (newRiskLevel < totalRiskLevel.getOrDefault(adj, Int.MAX_VALUE)){
                totalRiskLevel[adj] = newRiskLevel
                priorityQueue.add(VertexDistancePair(adj,newRiskLevel))
            }
        }
    }

    return totalRiskLevel[Pair(matrix.size - 1, matrix[0].size - 1)]!!
}

fun main() {

    fun part1(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length
        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)


        return dijkstra(Pair(0, 0), matrix)
    }

    fun part2(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length
        val matrix = Array(rowSize * 5) { IntArray(colSize * 5) }

        fun Int.nextLevel(): Int = if (this > 9) abs(this - 9) else this

        for (row in 0 until rowSize){
            for (col in 0 until colSize){
                var counter = 1
                matrix[row][col] = inputs[row][col].digitToInt()

                repeat(4){
                    val newCol = col + (colSize * counter)
                    val newRow = row + (rowSize * counter)
                    matrix[row][newCol] = (matrix[row][col] + it + 1).nextLevel()
                    matrix[newRow][col] = (matrix[row][col] + it + 1).nextLevel()

                    var newCounter = 1
                    repeat(4){ count ->
                        matrix[row + (rowSize * newCounter)][col + (colSize * counter)] = (matrix[row][newCol] + count + 1).nextLevel()
                        ++newCounter
                    }

                    ++counter
                }
            }
        }


        return dijkstra(Pair(0, 0), matrix)
    }

    val testInput = readInput("/day15/Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("/day15/Day15")
    println(part1(input))
    println(part2(input))
}
