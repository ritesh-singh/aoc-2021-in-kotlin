package day05

import readInput
import kotlin.math.abs

fun main() {

    data class Point(
        val x: Int,
        val y: Int,
    ) {
        // this will not be used in equals, as compiler only considers props from primary constructor
        var linesCoveringThisPoint: Int = 0
    }

    // Diagonal are not considered
    fun checkIfHorizontalOrVerticalLineIsFormed(point1: Point, point2: Point): Boolean {
        return (point1.x == point2.x) || (point1.y == point2.y)
    }

    fun checkIfDiagonalLineIsFormed(point1: Point, point2: Point): Boolean {
        return abs(point1.x - point2.x) == abs(point1.y - point2.y)
    }

    fun checkIfXCoordsAreEqual(point1: Point, point2: Point) = point1.x == point2.x

    fun checkIfYCoordsAreEqual(point1: Point, point2: Point) = point1.y == point2.y

    fun checkIfSetContainsPoint(point: Point, listOfCoordinates: MutableSet<Point>) {
        if (listOfCoordinates.contains(point)) {
            ++listOfCoordinates.elementAt(listOfCoordinates.indexOf(point)).linesCoveringThisPoint
        } else {
            ++point.linesCoveringThisPoint
            listOfCoordinates.add(point)
        }
    }

    // Find the cover points between two points
    fun coverPointsForHorizontalOrVerticalLine(point1: Point, point2: Point, listOfCoordinates: MutableSet<Point>) {
        if (checkIfXCoordsAreEqual(point1, point2) && checkIfYCoordsAreEqual(point1, point2)) return

        if (checkIfYCoordsAreEqual(point1, point2)) {
            val max: Int
            var x = if (point1.x < point2.x) {
                max = point2.x
                point1.x
            } else {
                max = point1.x
                point2.x
            }

            while (++x < max) {
                val coveringPoint = Point(x, point1.y)
                checkIfSetContainsPoint(coveringPoint, listOfCoordinates)
            }

            return
        }

        val max: Int
        var y = if (point1.y < point2.y) {
            max = point2.y
            point1.y
        } else {
            max = point1.y
            point2.y
        }

        while (++y < max) {
            val coveringPoint = Point(point1.x, y)
            checkIfSetContainsPoint(coveringPoint, listOfCoordinates)
        }

    }

    // 0,9 -> 5,9
    // 8,0 -> 0,8
    // 9,4 -> 3,4
    // 2,2 -> 2,1
    // 7,0 -> 7,4
    // 6,4 -> 2,0
    // 0,9 -> 2,9
    // 3,4 -> 1,4
    // 0,0 -> 8,8
    // 5,5 -> 8,2
    fun part1(inputs: List<String>): Int {

        val listOfCoordinates = mutableSetOf<Point>()

        for (input in inputs) {
            val splitInput = input.split(" ").filter { it != "->" }
            val point1 = splitInput[0].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }
            val point2 = splitInput[1].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }

            // 1 - Check if horizontal or vertical line is formed
            val isLineFormed = checkIfHorizontalOrVerticalLineIsFormed(point1, point2)
            if (isLineFormed) {
                checkIfSetContainsPoint(point1, listOfCoordinates)
                checkIfSetContainsPoint(point2, listOfCoordinates)
                coverPointsForHorizontalOrVerticalLine(point1, point2, listOfCoordinates)
            }
        }

        return listOfCoordinates.sortedByDescending {
            it.linesCoveringThisPoint
        }.count {
            it.linesCoveringThisPoint >= 2
        }
    }

    fun part2(inputs: List<String>): Int {
        val listOfCoordinates = mutableSetOf<Point>()

        for (input in inputs) {
            val splitInput = input.split(" ").filter { it != "->" }
            val point1 = splitInput[0].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }
            val point2 = splitInput[1].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }

            val isLineFormed = checkIfHorizontalOrVerticalLineIsFormed(point1, point2)
            if (isLineFormed) {
                checkIfSetContainsPoint(point1, listOfCoordinates)
                checkIfSetContainsPoint(point2, listOfCoordinates)
                coverPointsForHorizontalOrVerticalLine(point1, point2, listOfCoordinates)
            } else if (checkIfDiagonalLineIsFormed(point1, point2)) {
                checkIfSetContainsPoint(point1, listOfCoordinates)
                checkIfSetContainsPoint(point2, listOfCoordinates)

                var x = if (point1.x > point2.x) point1.x - 1 else point1.x + 1
                var y = if (point1.y > point2.y) point1.y -1 else point1.y + 1
                while (x != point2.x){
                    checkIfSetContainsPoint(Point(x, y), listOfCoordinates)
                    x = if (x > point2.x) x - 1 else x + 1
                    y = if (y > point2.y) y -1 else y + 1
                }
            }
        }

        return listOfCoordinates.sortedByDescending {
            it.linesCoveringThisPoint
        }.count {
            it.linesCoveringThisPoint >= 2
        }
    }

    val testInput = readInput("/day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("/day05/Day05")
    println(part1(input))
    println(part2(input))
}