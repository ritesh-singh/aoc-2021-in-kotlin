package day22

import readInput

fun main() {
    val validRange: IntRange = 0..101

    fun IntRange.isValid(): Boolean = this.first >= validRange.first && this.last < validRange.last

    fun part1(inputs: List<String>): Int {
        val threeDGrid = Array(101) { Array(101) { Array(101) { false } } }
        var total = 0L
        for (input in inputs) {
            val (action, coords) = input.split(" ").map { str -> str.trim() }
            val (xR, yR, zR) = coords.split(",").map { str ->
                str.trim().substringAfter("=").trim().split("..").let { it[0].toInt() + 50..it[1].toInt() + 50 }
            }
            if (!(xR.isValid() && yR.isValid() && zR.isValid())) continue
            for (x in xR)
                for (y in yR)
                    for (z in zR)
                        when (action) {
                            "on" -> {
                                if (!threeDGrid[x][y][z]) total++
                                threeDGrid[x][y][z] = true
                            }
                            "off" -> {
                                if (threeDGrid[x][y][z]) total--
                                threeDGrid[x][y][z] = false
                            }
                        }
        }

        println(total)

        return inputs.size
    }

    val testInput = readInput("/day22/Day22_test")
    part1(testInput)


    val input = readInput("/day22/Day22")
    part1(input)
}
