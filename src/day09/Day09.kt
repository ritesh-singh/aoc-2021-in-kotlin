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

    fun Array<IntArray>.getRiskLevelAndLowPointLocation():Pair<Int,List<Pair<Int,Int>>> {
        val rowSize = this.size
        val colSize = this[0].size

        var riskLevel = 0
        val listOfLowPointLocations = mutableListOf<Pair<Int, Int>>()

        // find lowest points
        for (row in 0 until rowSize) {
            for (col in 0 until colSize) {
                val current = this[row][col]
                var isLowest = false
                for (direction in Direction.values()){
                    val pair = getLocationBasedOnDirection(row, col, direction)
                    if (isLocationValid(pair.first, pair.second,rowSize, colSize)) {
                        if (current <  this[pair.first][pair.second]){
                            isLowest = true
                        }else{
                            isLowest = false
                            break
                        }
                    }
                }
                if (isLowest) {
                    riskLevel += current + 1
                    listOfLowPointLocations.add(Pair(row,col))
                }
            }
        }

        return Pair(riskLevel,listOfLowPointLocations)
    }

    fun part1(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length

        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)

        return matrix.getRiskLevelAndLowPointLocation().first
    }

    // Required to check if it contains any location in bing
    val allLocationsInBasin = mutableListOf<Pair<Int, Int>>()

    data class BasinCounter(var count: Int = 0)

    // Locations of height 9 do not count as being in any basin
    // all other locations will always be part of exactly one basin (row wise)
    fun getBasin(matrix: Array<IntArray>, lowPoint: Pair<Int, Int>, counter: BasinCounter) {
        val rowSize = matrix.size
        val colSize = matrix[0].size

        for (direction in Direction.values()) {
            val currentLocation = matrix[lowPoint.first][lowPoint.second]
            val locFromDirection = getLocationBasedOnDirection(lowPoint.first,lowPoint.second, direction)
            val isLocValid = isLocationValid(locFromDirection.first,locFromDirection.second,rowSize, colSize)
            if (
                isLocValid
                && matrix[locFromDirection.first][locFromDirection.second] > currentLocation
                && matrix[locFromDirection.first][locFromDirection.second] != 9
                && !allLocationsInBasin.contains(locFromDirection)
            ){
                allLocationsInBasin.add(locFromDirection)
                counter.count = counter.count + 1
                getBasin(matrix,locFromDirection,counter)
            }
        }
    }

    fun part2(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length

        val matrix = Array(rowSize) { IntArray(colSize) }
        inputs.initMatrix(matrix = matrix)

        val lowestPoints = matrix.getRiskLevelAndLowPointLocation().second

        val listOfBasins = mutableListOf<Int>()
        // for each low point find basin
        for (lowPoint in lowestPoints) {
            allLocationsInBasin.add(Pair(lowPoint.first, lowPoint.second))
            // every low point has a basin
            // The size of a basin is the number of locations within the basin,
            // including the low point. (count = 1) -> includes low point count
            val counter = BasinCounter()
            counter.count = 1
            getBasin(matrix = matrix, lowPoint = lowPoint, counter = counter)
            listOfBasins.add(counter.count)
        }

        listOfBasins.sortDescending()

        return listOfBasins.take(3).reduce { acc, i -> acc * i }
    }

    val testInput = readInput("/day09/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("/day09/Day09")
    println(part1(input))
    println(part2(input))
}
