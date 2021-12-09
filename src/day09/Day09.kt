package day09

import readInput

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

fun main(){

    val getLocationBasedOnDirection: (row: Int, col: Int, direction: Direction) -> Pair<Int, Int> =
        { row, col, direction ->
            val pair = when (direction) {
                Direction.UP -> Pair(row - 1, col)
                Direction.DOWN -> Pair(row + 1, col)
                Direction.LEFT -> Pair(row, col - 1)
                Direction.RIGHT -> Pair(row, col + 1)
            }
            pair
        }

    val isLocationValid: (row: Int, col: Int, rowSize: Int, colSize: Int) -> Boolean = { row, col, rowSize, colSize ->
        (row in 0 until rowSize) && (col in 0 until colSize)
    }

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

    fun part1(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length

        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)

        var totalCount = 0

        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                val current = matrix[row][col]
                var isLowest = false
                for (direction in Direction.values()){
                    val pair = getLocationBasedOnDirection(row, col, direction)
                    if (isLocationValid(pair.first, pair.second,rowSize, colSize)) {
                        if (current <  matrix[pair.first][pair.second]){
                            isLowest = true
                        }else{
                            isLowest = false
                            break
                        }
                    }
                }
                if (isLowest){
                    totalCount += current + 1
                }
            }
        }

        return totalCount
    }

    fun part2(inputs: List<String>): Int {
        return inputs.size
    }


    val testInput = readInput("/day09/Day09_test")
    check(part1(testInput) == 15)
//    check(part2(testInput) == 1134)

    val input = readInput("/day09/Day09")
    println(part1(input))
//    println(part2(input))
}
