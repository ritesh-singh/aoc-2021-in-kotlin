package day05

import readInput
import kotlin.math.abs

fun main() {

    data class Point(
        val x: Int,
        val y: Int,
    )

    // Diagonal are not considered
    fun checkIfHorizontalOrVerticalLineIsFormed(point1: Point, point2: Point): Boolean {
        return (point1.x == point2.x) || (point1.y == point2.y)
    }

    fun checkIfDiagonalLineIsFormed(point1: Point, point2: Point): Boolean {
        return abs(point1.x - point2.x) == abs(point1.y - point2.y)
    }

    fun checkIfXCoordsAreEqual(point1: Point, point2: Point) = point1.x == point2.x

    fun checkIfYCoordsAreEqual(point1: Point, point2: Point) = point1.y == point2.y

    fun addCoordinateInMap(point: Point, hashMap: HashMap<Point,Int>) {
        if (hashMap.containsKey(point)){
            hashMap[point] = hashMap[point]!!.plus(1)
        }else{
            hashMap[point] = 1
        }
    }

    // Find the cover points between two points
    fun coverPointsForHorizontalOrVerticalLine(point1: Point, point2: Point, hashMap: HashMap<Point,Int>) {
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
                addCoordinateInMap(coveringPoint, hashMap)
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
            addCoordinateInMap(coveringPoint, hashMap)
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

        val mapOfCoordinates = hashMapOf<Point,Int>()

        for (input in inputs) {
            val splitInput = input.split(" ").filter { it != "->" }
            val point1 = splitInput[0].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }
            val point2 = splitInput[1].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }

            // 1 - Check if horizontal or vertical line is formed
            val isLineFormed = checkIfHorizontalOrVerticalLineIsFormed(point1, point2)
            if (isLineFormed) {
                addCoordinateInMap(point1, mapOfCoordinates)
                addCoordinateInMap(point2, mapOfCoordinates)
                coverPointsForHorizontalOrVerticalLine(point1, point2, mapOfCoordinates)
            }
        }

        return mapOfCoordinates.count {
            it.value >= 2
        }
    }

    fun part2(inputs: List<String>): Int {
        val mapOfCoordinates = hashMapOf<Point,Int>()

        for (input in inputs) {
            val splitInput = input.split(" ").filter { it != "->" }
            val point1 = splitInput[0].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }
            val point2 = splitInput[1].split(",").map { it.toInt() }.let { Point(x = it[0], y = it[1]) }

            val isLineFormed = checkIfHorizontalOrVerticalLineIsFormed(point1, point2)
            if (isLineFormed) {
                addCoordinateInMap(point1, mapOfCoordinates)
                addCoordinateInMap(point2, mapOfCoordinates)
                coverPointsForHorizontalOrVerticalLine(point1, point2, mapOfCoordinates)
            } else if (checkIfDiagonalLineIsFormed(point1, point2)) {
                addCoordinateInMap(point1, mapOfCoordinates)
                addCoordinateInMap(point2, mapOfCoordinates)

                val calculateMovement: (Int,Int) -> Int = { value1,value2 ->
                    if (value1 > value2) value1 - 1 else value1 + 1
                }

                var x = calculateMovement(point1.x,point2.x)
                var y = calculateMovement(point1.y,point2.y)
                while (x != point2.x){
                    addCoordinateInMap(Point(x, y), mapOfCoordinates)
                    x = calculateMovement(x,point2.x)
                    y = calculateMovement(y,point2.y)
                }
            }
        }

        return mapOfCoordinates.count {
            it.value >= 2
        }
    }

    val testInput = readInput("/day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("/day05/Day05")
    println(part1(input))
    println(part2(input))
}
