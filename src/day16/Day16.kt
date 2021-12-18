package day16

import readInput
import java.lang.IllegalStateException

fun main() {

    var totalVersion = 0

    fun String.binaryToDigit() = this.toInt(2)

    fun String.getNBit(startIndex: Int, numberOfBit: Int) =
        this.slice(startIndex until startIndex + numberOfBit).binaryToDigit()

    fun parsePacketsPart1(packet: String): Int {
        var currentIndex = 0
        val getNBit: (startIndex: Int, numberOfBit: Int) -> Int =
            { s, n -> packet.getNBit(s, n).also { currentIndex += n } }

        totalVersion += getNBit(0, 3) // version

        when (getNBit(3, 3)) { // typeId
            4 -> {
                while (packet[currentIndex] != '0') getNBit(currentIndex, 5)
                getNBit(currentIndex, 5)
                return currentIndex
            }
            else -> {
                when (packet[currentIndex++]) {
                    '0' -> {
                        var index = currentIndex
                        val totalLengthInBits = getNBit(currentIndex, 15).also { index += 15 }
                        var subPacketLength = 0
                        while (true) {
                            subPacketLength += parsePacketsPart1(packet.substring(currentIndex + subPacketLength, packet.length))
                            if (subPacketLength == totalLengthInBits) break
                        }
                        return index + subPacketLength
                    }
                    '1' -> {
                        var indexToReturn = currentIndex
                        val next11Bits = getNBit(currentIndex, 11).also { indexToReturn += 11 }
                        var counter = 0
                        var index = 0
                        while (next11Bits != counter++) {
                            index += parsePacketsPart1(packet.substring(currentIndex + index, packet.length))
                        }
                        return indexToReturn + index
                    }
                }
            }
        }

        return 0
    }

    fun part1(inputs: List<String>): Int {
        totalVersion = 0
        val bitsTransmissionInBinary = inputs[0]
            .map {
                it.digitToInt(16).toString(2).padStart(4, '0')
            }.joinToString("")

        parsePacketsPart1(packet = bitsTransmissionInBinary)

        return totalVersion
    }

    val performCalculation: (typeId: Int, list: List<Long>) -> Long = { typeId, list ->
        when (typeId) {
            0 -> list.sum()
            1 -> list.reduce { acc, i -> acc * i }
            2 -> list.minOrNull()!!
            3 -> list.maxOrNull()!!
            5 -> if (list[0] > list[1]) 1 else 0
            6 -> if (list[0] > list[1]) 0 else 1
            7 -> if (list[0] == list[1]) 1 else 0
            else -> throw IllegalStateException()
        }
    }

    fun part2(inputs: List<String>): Long {
        totalVersion = 0
        val bitsTransmissionInBinary = inputs[0]
            .map {
                it.digitToInt(16).toString(2).padStart(4, '0')
            }.joinToString("")

        return 0
    }

    val testInput = readInput("/day16/Day16_test")
//    check(part1(testInput) == 31)

    part2(testInput)
//    println(part2(testInput))

    val input = readInput("/day16/Day16")
    println(part1(input))
    println(part2(input))

}

