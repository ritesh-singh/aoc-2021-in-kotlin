package day20

import readInput
import java.lang.StringBuilder

fun main() {
    val lightPixel = '#'
    val darkPixel = '.'

    fun Char.isLightPixel() = this == '#'
    fun Char.isDarkPixel() = this == '.'

    fun Pair<Int, Int>.isValid(rowSize: Int, colSize: Int) = (first in 0 until rowSize) && (second in 0 until colSize)

    fun Array<CharArray>.initInputGrid(defaultPixel: Char): Array<CharArray> {
        var newInputGrid = Array(this.size + 2) { CharArray(this[0].size + 2) { defaultPixel } }
        for (row in 1..this.size) {
            for (col in 1..this[0].size) {
                newInputGrid[row][col] = this[row - 1][col - 1]
            }
        }
        return newInputGrid
    }

    fun List<String>.initGrid():Array<CharArray>{
        val grid = Array(this.size) { CharArray(this[0].length) }
        for (row in this.indices) {
            for (col in 0 until this[0].length) {
                grid[row][col] = this[row][col]
            }
        }
        return grid
    }

    fun solution(inputs: List<String>, times: Int): Int {
        val algorithm = inputs[0]

        val inputList = inputs.subList(2,inputs.size)
        val inputImage = inputList.initGrid()


        var flipsTo = darkPixel
        var result = 0

        var inputGrid = inputImage.initInputGrid(darkPixel)
        var outputGrid = Array(inputList.size + 2) { CharArray(inputList[0].length + 2) }
        repeat(times) {
            result = 0
            for (row in inputGrid.indices){
                for (col in 0 until inputGrid[0].size){
                    listOf(
                        Pair(row - 1, col - 1), Pair(row - 1, col), Pair(row - 1, col + 1),
                        Pair(row, col - 1), Pair(row, col), Pair(row, col + 1),
                        Pair(row + 1, col - 1), Pair(row + 1, col), Pair(row + 1, col + 1)
                    ).chunked(3).joinToString("") { list ->
                        val str = StringBuilder()
                        list.forEach {
                            if (it.isValid(inputGrid.size, inputGrid[0].size)) {
                                if (inputGrid[it.first][it.second].isDarkPixel()) str.append(0) else str.append(1)
                            } else {
                                if (flipsTo == darkPixel) str.append(0) else str.append(1)
                            }
                        }
                        str
                    }.toInt(2).let {
                        if (algorithm[it].isLightPixel()) result++
                        outputGrid[row][col] = algorithm[it]
                    }
                }
            }

            flipsTo = if (flipsTo == darkPixel) lightPixel else darkPixel
            inputGrid = outputGrid.initInputGrid(flipsTo)
            outputGrid = Array(inputGrid.size) { CharArray(inputGrid[0].size) }
        }

        return result
    }


    val input = readInput("/day20/Day20")
    println(solution(input,times = 2))
    println(solution(input,times = 50))
}