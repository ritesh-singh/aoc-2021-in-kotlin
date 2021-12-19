package day17

import readInput

fun main() {

    fun fireProbe(xVel: Int, yVel: Int, xTargetRange: IntRange, yTargetRange: IntRange): Pair<Boolean, Int> {
        var (xPos, yPos, xVelocity, yVelocity) = listOf(0, 0, xVel, yVel)
        var maxY = Int.MIN_VALUE
        while (!(xPos in xTargetRange && yPos in yTargetRange) && xPos <= xTargetRange.last && yPos >= yTargetRange.first) {
            xPos += xVelocity
            yPos += yVelocity
            if (yPos > maxY) maxY = yPos
            xVelocity += if (xVelocity > 0) -1 else if (xVelocity < 0) 1 else 0
            yVelocity--
        }
        return Pair((xPos in xTargetRange && yPos in yTargetRange), maxY)
    }

    var maxHeight = Int.MIN_VALUE
    var steps = 0

    fun solution(inputs: List<String>) {
        val (first, second) = inputs[0].removePrefix("target area:").split(",").map { it.trim() }
        val (xMin, xMax) = first.removePrefix("x=").split("..").map { it.trim().toInt() }
        val (yMin, yMax) = second.removePrefix("y=").split("..").map { it.trim().toInt() }

        for (xVel in 1..xMax) {
            for (yVel in yMin..200) {
                val (isInRange, value) = fireProbe(xVel, yVel, xMin..xMax, yMin..yMax)
                if (isInRange) {
                    steps++
                    if (value > maxHeight) {
                        maxHeight = value
                    }
                }
            }
        }
    }

    val input = readInput("/day17/Day17")
    solution(input)
    println(maxHeight)
    println(steps)
}
