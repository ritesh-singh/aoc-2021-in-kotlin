package day25

import readInput

fun main() {

    val emptySpace = '.'
    val eastFacing = '>'
    val southFacing = 'v'

    fun Array<CharArray>.deepCopy() = Array(size) { get(it).clone() }

    fun List<String>.initGrid(inputGrid: Array<CharArray>,outPutGrid: Array<CharArray>) {
        for (row in this.indices) {
            for (col in 0 until this[0].length) {
                inputGrid[row][col] = when (this[row][col]) {
                    emptySpace -> emptySpace
                    eastFacing -> eastFacing
                    southFacing -> southFacing
                    else -> throw IllegalStateException()
                }
                outPutGrid[row][col] = inputGrid[row][col]
            }
        }
    }

    fun part1(inputs: List<String>): Int {
        val rowSize = inputs.size
        val colSize = inputs[0].length
        var inputGrid = Array(inputs.size) { CharArray(inputs[0].length) }
        val outPutGrid = Array(inputs.size) { CharArray(inputs[0].length) }

        inputs.initGrid(inputGrid, outPutGrid)

        var steps = 0
        var moving = true
        while (moving) {
            if (moving) steps++
            moving = false

            // move all east facing
            for (rowIndex in 0 until rowSize) {
                var colIndex = 0
                while (colIndex < colSize) {
                    if (inputGrid[rowIndex][colIndex] == eastFacing) {
                        while (++colIndex < colSize && inputGrid[rowIndex][colIndex] == eastFacing) continue
                        if (colIndex == colSize) {
                            if (inputGrid[rowIndex][0] == emptySpace) {
                                moving = true
                                outPutGrid[rowIndex][0] = eastFacing
                                outPutGrid[rowIndex][colIndex - 1] = emptySpace
                            }
                        } else {
                            if (inputGrid[rowIndex][colIndex] == emptySpace) {
                                moving = true
                                outPutGrid[rowIndex][colIndex - 1] = emptySpace
                                outPutGrid[rowIndex][colIndex] = eastFacing
                                colIndex++
                            }
                        }
                    } else {
                        ++colIndex
                    }
                }
            }


            inputGrid = outPutGrid.deepCopy()

            // move all south facing
            for (colIndex in 0 until colSize) {
                var rowIndex = 0
                while (rowIndex < rowSize) {
                    if (inputGrid[rowIndex][colIndex] == southFacing){
                        while (++rowIndex < rowSize && inputGrid[rowIndex][colIndex] == southFacing) continue
                        if (rowIndex == rowSize){
                            if (inputGrid[0][colIndex] == emptySpace) {
                                moving = true
                                outPutGrid[0][colIndex] = southFacing
                                outPutGrid[rowIndex-1][colIndex] = emptySpace
                            }
                        }else{
                            if (inputGrid[rowIndex][colIndex] == emptySpace) {
                                moving = true
                                outPutGrid[rowIndex-1][colIndex] = emptySpace
                                outPutGrid[rowIndex][colIndex] = southFacing
                                rowIndex++
                            }
                        }
                    } else {
                        ++rowIndex
                    }
                }
            }

            inputGrid = outPutGrid.deepCopy()
        }

        println(steps)

        return inputs.size
    }

    val testInput = readInput("/day25/Day25_test")
    part1(testInput)

    val input = readInput("/day25/Day25")
    part1(input)
}