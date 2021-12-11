package day11

import readInput

private enum class Direction {
    UP, DOWN, LEFT, RIGHT, DIAGUPLEFT, DIAGUPRIGHT, DIAGDOWNLEFT, DIAGDOWNRIGHT
}

fun main() {

    fun List<String>.initMatrix(matrix: Array<IntArray>) {
        val rowSize = size
        val colSize = this[0].length
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                matrix[row][col] = this[row][col].digitToInt()
            }
        }
    }

    fun Array<IntArray>.printMatrix() {
        println("Print Matrix")
        for (i in 0 until this.size){
            for (j in 0 until this[0].size){
                print(this[i][j])
                print(" ")
            }
            println()
        }
    }

    val getLocationBasedOnDirection: (row: Int, col: Int, direction: Direction) -> Pair<Int, Int> =
        { row, col, direction ->
            val pair = when (direction) {
                Direction.UP -> Pair(row - 1, col)
                Direction.DOWN -> Pair(row + 1, col)
                Direction.LEFT -> Pair(row, col - 1)
                Direction.RIGHT -> Pair(row, col + 1)
                Direction.DIAGUPLEFT -> Pair(row - 1, col - 1)
                Direction.DIAGUPRIGHT -> Pair(row - 1, col + 1)
                Direction.DIAGDOWNLEFT -> Pair(row + 1, col - 1)
                Direction.DIAGDOWNRIGHT -> Pair(row + 1, col + 1)
            }
            pair
        }

    val isLocationValid: (row: Int, col: Int, rowSize: Int, colSize: Int) -> Boolean = { row, col, rowSize, colSize ->
        (row in 0 until rowSize) && (col in 0 until colSize)
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

    fun Int.newEnergyLevel(): Int = if (this == 9) 0 else this + 1

    fun Int.flashes(): Boolean = this == 0

    fun updateAdjacent(matrix: Array<IntArray>, currentRow: Int, currentCol: Int, flashedList: MutableList<Pair<Int, Int>>) {
        for (adj in matrix.getAdjacent(currentRow, currentCol)) {
            if (!flashedList.contains(Pair(adj.first, adj.second))) {
                matrix[adj.first][adj.second] = matrix[adj.first][adj.second].newEnergyLevel()
                if (matrix[adj.first][adj.second].flashes()) {
                    flashedList.add(Pair(adj.first,adj.second))
                    updateAdjacent(matrix,adj.first,adj.second,flashedList)
                }
            }
        }
    }

    fun part1(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length

        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)

        var totalFlashes = 0

        repeat(100){
            val flashedList = mutableListOf<Pair<Int, Int>>()
            for (row in 0 until rowSize){
                for (col in 0 until colSize){
                    if (!flashedList.contains(Pair(row,col))){
                        matrix[row][col] = matrix[row][col].newEnergyLevel()
                        if (matrix[row][col].flashes()){
                            flashedList.add(Pair(row,col))
                            updateAdjacent(matrix,row,col,flashedList)
                        }
                    }
                }
            }
            totalFlashes += flashedList.size
        }

        return totalFlashes
    }

    fun part2(inputs: List<String>): Int {
        return inputs.size
    }

    val testInput = readInput("/day11/Day11_test")
    check(part1(testInput) == 1656)

    val input = readInput("/day11/Day11")
    println(part1(input))
}
